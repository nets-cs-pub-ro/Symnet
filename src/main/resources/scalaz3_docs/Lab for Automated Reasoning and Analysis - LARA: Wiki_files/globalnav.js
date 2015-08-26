/*
 * EPFL navigation
 *
 * Copyright (c) 2010-2013 EPFL
 *
 * Date: 2013-09-06 16:00
 * Revision: 1.5
 */
function getPeopleAutocomplete() {
  "use strict";
  var field = jQuery("#searchfield");
  field.autocomplete({
      source: function(request, response) {
          jQuery.ajax({
                       url: "http://search.epfl.ch/json/autocompletename.action",
                       dataType: 'jsonp',
                       data: { maxRows: 15, term: request.term },
                       success: function(data){
                           response(jQuery.map(data.result, function(item) {
                              return {
                                      label: item.name + ', ' + item.firstname,
                                      value: item.firstname + ' ' + item.name
                                      };
                            }));
                  
                       }
          });
      },
      appendTo: jQuery('#searchform'),
      disabled: false,
      minLength: 3,
      select: function(event, ui) {
          if (ui.item) { field.val(ui.item.value);}
          jQuery('#searchform').submit();
      }
  });
}


var current_search_base = null;


function showPanel() {
    jQuery("#header").expose({color: '#000', opacity: 0.6, loadSpeed: 0, closeSpeed: 0});
    jQuery('.navigation-panel').addClass("hidden");
    jQuery(this).children('.navigation-panel').removeClass('hidden');
}

function hidePanel(){}

function removeLastWord(element) {
    var text = element.html();
    var i = text.lastIndexOf(' ');
    element.html(text.substring(0, i) + '...');
    return i !== -1;
}

/* add arrows when news texts are too long */
function isTotallyVisible(parent, element) {
    return element.position().top + element.outerHeight() + 2 < parent.position().top + parent.innerHeight();
}


function change_search_base(radio){
    jQuery('#search-options').remove();
    jQuery("#searchform input[type=radio]").removeAttr("checked");  
    var rid = radio.attr('id');
    var label =  jQuery('label[for=' + rid + ']');
    jQuery("#searchfield").autocomplete({ disabled: true });
    
    switch (rid){
        case "search-engine-person":
            getPeopleAutocomplete();
            break;
        case "search-engine-local":
            jQuery('#searchform').append(
                jQuery("<input/>").attr("type", "hidden").attr("id","search-options").attr("name", "as_sitesearch").attr("value", jQuery.url.attr("host"))
            );
            break;
        default:
            break;
    }
    
    if (jQuery('#searchfield').val() === jQuery('#searchform label.current').attr('title')) { jQuery('#searchfield').val(''); }
    if (jQuery('#searchfield').val() === '') { jQuery('#searchfield').val(label.attr('title')); }
    
    current_search_base.toggleClass('current');
    current_search_base = label;
    current_search_base.toggleClass('current');
    radio.attr('checked','checked');
    radio.blur();
    if (document.referrer.indexOf('#') !== -1) {
        radio.focus();
    }
}




jQuery.fn.exists = function(){ return this.length>0; };
jQuery(document).ready(function($){
  "use strict";
  // Big panels
  var showPane = function(pane){
    $('.navigation-panel').addClass("hidden"); // close all
    $(pane).removeClass('hidden');
    $('#header2013').expose({color: '#000', opacity: 0.6, loadSpeed: 0, closeSpeed: 0});
  };

  var closePane = function(){
    if ($(".navigation-panel:not(.hidden)").length === 0) { return; }
    $('.navigation-panel').addClass("hidden");
    $.mask.close();
  };

  if ($('#header').exists()){
    /* Header - Web 2010 */
    
    // Big panels
    $('#header').mouseleave(function(){
      $.mask.close();
      $('.navigation-panel').addClass("hidden");
    });
    var config = { over: showPanel, out: hidePanel, timeout: 500 };
      $('#header .menu').hoverIntent(config);
      $("#header .main-link").click(function(){ return false; });
      $('#header').mouseleave(closePane);

    // Search box
    $('#searchform').submit(function() {
      if ($('#searchfield').val() === $('#searchform label.current').attr('title')) {
        $('#searchfield').val('');
      }
    });
    current_search_base = $('#header #searchform label.current');
    change_search_base($("#header #search-engine-person"));
    $('#search-box input[type=radio]').change(function(){ change_search_base($(this)); });
    $('#searchfield').focus(function(){ if ($(this).val() === current_search_base.attr('title')){ $(this).val('').addClass('focused');} });
    if ($.browser.msie) {
      $('#search-box input[type=radio]').click(function(){ change_search_base($(this)); this.blur(); this.focus(); });
    }
    /* Make labels clickable under mobile safari*/
    if (navigator.userAgent.match(/iPhone/i) || navigator.userAgent.match(/iPod/i) || navigator.userAgent.match(/iPad/i)) {
      $('#search-box label').click(function () { var el = $(this).attr('for'); change_search_base($('#' + el));});
    }

  } else {
    /* Header - Web 2013 */
    
    $("#header2013 #nav-menus .menu").click(function(){
        var pane = $('.navigation-panel', this);
        if (pane.hasClass('hidden')){
          showPane(pane);
        } else {
          closePane();
        }
        return false; // do not propagate
    });
    $('#header2013 .navigation-panel').click(function(event){
        event.stopPropagation();
    });
    $(document).keyup(function(e){
      if (e.keyCode === 27) { // escape key 
        closePane();
      }
    });
    $('html').click(closePane);
  
    // Search box
    $('#header2013 .selected-field').click(function(){ $(this).siblings('ul').toggleClass('hidden'); });
    $('#header2013 .search-filter').mouseleave(function(){ $(this).children('ul').addClass('hidden'); });
    $('#header2013  #nav-search input[type="radio"]').click(function(){
      $(this).parent().parent().addClass('hidden');
    });
    var setAutoComplete = function(){
      $("#header2013 #header_searchfield").autocomplete({
          source: function(request, response) {
              $.ajax({url: "http://search.epfl.ch/json/autocompletename.action",
                      dataType: 'jsonp',
                      data: { maxRows: 10, term: request.term },
                      success: function(data){
                        var res = $.map(data.result, function(item) {
                          return {label: item.name + ', ' + item.firstname,
                                  value: item.firstname + ' ' + item.name};
                        });
                        response(res);
                        if (data.hasMore){
                          var label = data.lang && data.lang==="en" ? "See all results": "Voir tous les résultats";
                          var link = "http://search.epfl.ch/psearch.action?q=" + data.term;
                          $('#header2013 #search-box ul.ui-autocomplete li').last().after('<li><a class="ac-more" href="' + link + '">' + label + '</a></li>'); 
                        }
                      }
              });
          },
          appendTo: $('#header_searchform'),
          disabled: false,
          minLength: 3,
          select: function(event, ui) {
              if (ui.item) { $(this).val(ui.item.value);}
              $('#header2013 #header_searchform').submit();
          }
      });
    };
    
    var unsetAutoComplete = function(){
      $("#header2013 #header_searchfield").autocomplete({ disabled: true });
    };
    
    $('#header2013 input[type="radio"]').change(function(){
      var label =  $('label[for=' + $(this).attr('id') + ']');
      $('#search-box .selected-field').text(label.text());
      
      if ($(this).is('#search-engine-person')){
        setAutoComplete();
      } else {
        unsetAutoComplete();
      }
    });
    setAutoComplete();
    $('#header2013 #search-engine-local').change(function(){
      $('#header_local_site').attr("value", jQuery.url.attr("host"));
    });
  } // end Web2010/2013


  $('#searchfield').blur(function() { if ($(this).val() === '') {
    $(this).val($('#searchform label.current').attr('title')).removeClass('focused');}
  });
  $('#searchfield').keypress(function(e){
    if (e.which === 13) {
      $(this).parent('form').submit();
    }
  });
  $('#searchlink').click(function(){
    $('#searchfield').focus();
  });
  
  
  /* navigation: Dropdown menus */
  $('.dropdown').click(function(){ $(this).children('ul').toggleClass('hidden'); });
  $('.dropdown').mouseleave(function(){ $(this).children('ul').addClass('hidden'); });
  $('#main-navigation .dropdown').hoverIntent(
      function(){ $(this).children('ul').removeClass('hidden');},
      function(){ $(this).children('ul').addClass('hidden');});
  $('#main-navigation .dropdown').click(function(){ return true;});

  /* navigation: tree */
  $(".tree li.inpath").addClass('open');
  $(".tree").treeview({ 'collapsed': true, 'unique': false });
  $(".tree").children().addClass('local-color');
  $(".tree li a").hover(
      function(e) { e.stopPropagation();
                    $('.tree li').removeClass("hover");
                    $(this).parent().addClass('hover');
                    },
      function(e) { e.stopPropagation(); $(this).parent().removeClass("hover");}
  );

  

  /* activate togglers */
  $('.toggler').click(function(){
    $(this).toggleClass("toggled-active").next().slideToggle("slow");
    return false;
  });
  
  /* modal windows */
  $(".modal-opener[rel]").overlay({mask: { color: '#000', opacity: 0.6, loadSpeed: 200},
                                   closeOnClick: false});

  /* Set correct margin to elements */
  $(".box.two-cols div.box-col:even",this).addClass("box-left-col");
  $(".box.two-cols div.box-col:odd", this).addClass("box-right-col");
  $("#content:not(.fullpage-content) .box:odd",this).addClass("last-col");

  /* add class .left to images having align="left" and so on. */
  $('img[align]').each(function(){ $(this).addClass($(this).attr('align')); });

      
  /* Overlay */
  $("img[rel]").overlay();
  
  /* News (actu.epfl.ch) */
  var newsDivs = $("div.news-text");
  newsDivs.each(function(i, news) {
    news = $(news);
    var newsText = news.find("p span.heading");
    var newsLink = news.find("p span.read-more");
    if (newsText.length && newsLink.length) {
      while (!isTotallyVisible(news, newsLink) && removeLastWord(newsText)){
        void(0);
      }
    }
  });
  
  
  /* Google Analytics */
  $.jGoogleAnalytics('UA-4833294-1', {topLevelDomain: '.epfl.ch'} );


  /* Jahia specific */
  $("#main-content ul").each(function(){
      var elem = $(this);
      if(elem.children().length === 0){
          elem.remove();
      }
  });

});
