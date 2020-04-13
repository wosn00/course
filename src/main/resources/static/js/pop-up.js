$(function() {
    //alert($(window).height());
    $('.ClickMe').click(function() {
        var data=this.getAttribute("data-chapter");
        console.log(data);
        $('#code').center();
        $('#code').find(".effect04").attr("data-chapter",data);
        $('#goodcover').show();
        $('#code').fadeIn();
    });
    $('#closebt').click(function() {
        $('#code').hide();
        $('#goodcover').hide();
    });
    $('#goodcover').click(function() {
        $('#code').hide();
        $('#goodcover').hide();
    });
    /*var val=$(window).height();
	var codeheight=$("#code").height();
    var topheight=(val-codeheight)/2;
	$('#code').css('top',topheight);*/
    jQuery.fn.center = function(loaded) {
        var obj = this;
        body_width = parseInt($(window).width());
        body_height = parseInt($(window).height());
        block_width = parseInt(obj.width());
        block_height = parseInt(obj.height());

        left_position = parseInt((body_width / 2) - (block_width / 2) + $(window).scrollLeft());
        if (body_width < block_width) {
            left_position = 0 + $(window).scrollLeft();
        };

        top_position = parseInt((body_height / 2) - (block_height / 2) + $(window).scrollTop());
        if (body_height < block_height) {
            top_position = 0 + $(window).scrollTop();
        };

        if (!loaded) {

            obj.css({
                'position': 'absolute'
            });
            obj.css({
                'top': ($(window).height() - $('#code').height()) * 0.5,
                'left': left_position
            });
            $(window).bind('resize', function() {
                obj.center(!loaded);
            });
            $(window).bind('scroll', function() {
                obj.center(!loaded);
            });

        } else {
            obj.stop();
            obj.css({
                'position': 'absolute'
            });
            obj.animate({
                'top': top_position
            }, 200, 'linear');
        }
    };



    //计组部分
    //选择题的跳转
    $(".choiceClick").click(function () {
        var attribute =this.getAttribute("data-chapter");
       var url="/jizu_choicequestion/"+attribute;
       window.location.href=url;
    });

    //简答题的跳转
    $(".summaryClick").click(function () {
        var attribute =this.getAttribute("data-chapter");
       var url="/jizu_summary/"+attribute;
       window.location.href=url;
    });

    //数构部分
    //选择题的跳转
    $(".shugou-choice-click").click(function () {
        var attribute =this.getAttribute("data-chapter");
        var url="/shugou_choicequestion/"+attribute;
        window.location.href=url;
    });

    //简答题的跳转
    $(".shugou-summary-click").click(function () {
        var attribute =this.getAttribute("data-chapter");
        var url="/shugou_summary/"+attribute;
        window.location.href=url;
    });



});

