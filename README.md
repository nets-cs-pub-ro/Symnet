NetVisor
========

## Usage

### Starting a new VM

`nv.sh start identity_file click_file requirements_file vm_name server_addr`

Where:

    1. identity_file is the public key of the client
    2. click_file is the .click that is about to run
    3. requirements_file is the set of static analysis tests that need to be performed
    4. vm_name is the id the client uses to refer to this VM instance
    5. server_addr is the URL of the start endpoint, this is _optional_ as it defaults to http://localhost:8080/start

Server-side checks:

    1. Is the client authorized ?
    2. Enough resources - this is where billing info may come to question ?
    3. Is the vm name unique (per client) ?
    4. Are the requirements met ?

Response:

    1. Status
    2. If ok -> echo the vm name back for the client to use as a handle.

### Stopping a VM

`nv.sh stop identity_file vm_name server_addr`

Where the params have the same meaning as above.

Server-side checks:

    1. Is the client authorized ?
    2. Is the vm machine running ?

Response:

    1. Status

## Architecture description

![System layout](https://dl.dropboxusercontent.com/u/10608856/images/architecture.png)

#### Idea

_Each platform may host a number of VMs. These can by dynamically deployed by the provider, but also by clients._

Every platform has one _ingress_ traffic source and one _egress_ traffic destination. All the traffic coming to or going out of a platform must pass through one of these. Ingress traffic can be originated in the Internet or in a private network of the provider where clients reside. This is also true for egress points: they pass traffic to the Internet or to the connected clients.  The provider can also link together a pair of egress and ingress points, in which case a source platform sends all its traffic to another.

_Egress and Ingress are network interfaces in a data center from/to which traffic is grabbed/sent._

The description of ingress, egress and link traffic is possible only by studying the network setup of a provider (i.e what traffic is sent to a platform etc.), nonetheless this is essential for being able to statically reason about the topology as a whole and run end to end (i.e. from a client to an Internet server or vice versa ) reachability analysis.

This network setup description is provided by the provider in the form of a set of rules in the following format:

`PlatformId-(in/out): rule (&& rule)*`

`PlatformId PlatformId:  rule (&& rule)*`

The rules obey the ’tcpdump’ format. For example:

`platform_A platform_B: tcp && src port 80`

This rule describes that all HTTP traffic reaching the egress of `platform_A` is sent to the ingress of `platform_B`.

If one wishes to express that the ingress of platform_A is reachable only by traffic originated in `10.0.0.0/16` the following rule should be formulated: `platform_A-in: src 10.0.0.0/16`.

In a setup involving more than one platform available these rules describe the traffic flow among them.  This is provided to the controller that oversees the process of dynamic VM deployment.

VMs can be dynamically deployed by issuing a request to the controller, providing the Click configuration file that describes the computation and a list of reachability and flow invariant requirements that have to be met.

Since a provider runs many platforms and each may be reachable by different traffic is easy to see that not every platform is suitable for the computation the client demands. For example, if a client wants to block TCP traffic from given source, a platform reachable only by UDP traffic is definitely not the answer.

Reachability rules must be able to state the following:

- Egress traffic of a given nature must reach a given element inside client’s VM

`reach clickElementName-portId: rule (&& rule)*`

For example if a client wants traffic from `IP 10.0.0.1` to reach `port 0` of ‘src’ Click element the following rule should be stated: `reach src-0: src 10.0.0.1`

- Egress traffic of a given nature must reach a given element inside client’s VM and then reach the client obeying a given set of rules.

`reach clickElementName-portId client: rule (&& rule)* ->  rule (&& rule)*`

For example if a client wants traffic from `IP 10.0.0.1` to reach `port 0` of ‘src’ Click element and then be forwarded to `TCP port 1000` of the client, the following rule should be stated: `reach src-0 client: src 10.0.0.1 -> tcp && dst port 1000`.

Reachability rules make possible to send certain traffic to a given VM and then, optionally, forward it to the client. This enforces that the processing desired by the client actually occurs.

_We support only reachability to output ports since reachability to an input port is simply checked by reachability to the previous output port._

But what happens if processing is performed properly but along the path to the client traffic is changed in a way not intended by the client? 

This potentially renders the previous processing invalid. For being able to prevent this, the controller must enforce invariant rules on network paths connecting a VM and a client.  An invariant rule is stated in the following format.

`invariant clickElementNameA-portId: headerField (&& headerField)*`

_header fields also obey tcpdump format_

This ensures that between a certain port of a given Click element and the client, a list of header fields are not changed.

For example, to say that between `port 0` of `src` Click element and the client, the IP destination does not change, one writes: `invariant src-0: src`.

Any number of reachability and/or invariant rules can be stated, the controller’s job is to find a platform where all are satisfied, and, in case it does, start the VM and route its expected traffic to it (using OpenFlow rules) and also operate changes to its model of the network.