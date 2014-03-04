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

### Architecture description

![System layout](https://dl.dropboxusercontent.com/u/10608856/images/architecture.png)
