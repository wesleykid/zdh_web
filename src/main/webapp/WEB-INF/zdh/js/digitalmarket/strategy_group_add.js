function guid2() {

    return $.ajax({
        url: server_context+"/get_id",
        cache: false,
        async: false
    }).responseText;
}
$(document).ready(function(){
    
    //设置左侧为可复制的
    $(".deviceLeft2 .deviceLeft_box").children().draggable({
        helper: "clone",
        scope: "zlg",
    });

    //设置右侧为拖拽存放区
    var i=0;
    $("#m1").droppable({
        scope:"zlg",
        drop:function (event , ui) {
            var left = parseInt(ui.offset.left - $(this).offset().left);
            var top = parseInt(ui.offset.top - $(this).offset().top);
            var tp=ui.draggable[0].dataset.type;
                    i++;
                    cls_str="node node"+tp+"css tasks";
                    var id = guid2();
                    $(this).append('<div class="'+cls_str+'" style="position: absolute" id="' + id + '" data-type="'+tp+'" data-id=" " >' + $(ui.helper).html() + '</div>');
                    $("#" + id).css("left", left).css("top", top);
                    jsPlumb.addEndpoint(id, { anchors: "Top" }, hollowCircle);
                    jsPlumb.addEndpoint(id, { anchors: "Bottom" }, hollowCircle);
                    jsPlumb.draggable(id);
                    jsPlumb.makeTarget(id, {
                        anchor: "Continuous"
                    });
                    $("#" + id).draggable({ containment: "parent",grid: [10, 10] });
                    doubleclick("#" + id,tp);
        }
    });

    //基本连接线样式
    var connectorPaintStyle = {
        lineWidth: 2,
        strokeStyle: "#61b8d0",
    };

    // 鼠标悬浮在连接线上的样式
    var connectorHoverStyle = {
        lineWidth: 2,
        strokeStyle: "green",
    };

    //端点的颜色样式
    var paintStyle = {
        fillStyle: "#ccc",
        radius: 10,
        lineWidth:6 ,
    };

    // 鼠标悬浮在端点上的样式
    var hoverPaintStyle = {
        fillStyle: "#aaa",
    };

    //设置连接端点和连接线
    var hollowCircle = {
        endpoint: ["Dot", { radius: 2 }],  //端点的形状
        connectorStyle: connectorPaintStyle,
        connectorHoverStyle: connectorHoverStyle,
        paintStyle: paintStyle,
        hoverPaintStyle: hoverPaintStyle ,
        isSource: true,    //是否可以拖动（作为连线起点）
        connector: ["StateMachine", { stub: [40, 60], gap: 10, cornerRadius: 5, alwaysRespectStubs: true }],  //连接线的样式种类有[Bezier],[Flowchart],[StateMachine ],[Straight ]
        isTarget: true,    //是否可以放置（连线终点）
        maxConnections: -1,    // 设置连接点最多可以连接几条线
        connectorOverlays:[
            [ "Arrow", { width:10, length:20, location:1, id:"arrow" } ],
            ["Custom", {
                create:function(component) {
                    return $('<span style="background:#fff;position:relative;z-index:999;cursor:pointer;"></span>');
                },
                location:0.5,
                id:"customOverlay",
            }],
        ],
    };

    //鼠标进入增加一个删除的小图标
    $("#m1").on("mouseenter", ".node", function () {
        $(this).append('<img src="img/jsplumb/close2.png"  style="position:absolute; right: 0; bottom: 0;" />');
        var widthnum = $(this).css("width").substr(0,5);
        $("img").css("right", 0).css("top", -10);
    });
    //鼠标离开小图标消失
    $("#m1").on("mouseleave", ".node", function () {
        $("img").remove();
    });
    //节点小图标的单击事件
    $("#m1").on("click", "img",function () {
        var div=$(this).parent();
        layer.confirm('确定要删除此节点吗', {
            btn: ['确定','取消'] //按钮
        }, function(index){
            jsPlumb.removeAllEndpoints(div.attr("id"));
            div.remove();
            layer.close(layer.index);
        }, function(){

        });
    });

    //连接线中的文字双击事件
    $("#deviceRight").on("click", "._jsPlumb_overlay", function () {
        var that=$(this);
        that.removeClass('_jsPlumb_overlay');
        var text = that.text();
        that.html("");
        that.append('<input type="text" id="myDropDown" value="' + text + '" />');
        $('#myDropDown').blur(function () {
            that.html($("#myDropDown").val());
            that.addClass('_jsPlumb_overlay')
        });
        return false
    });

    //连接线的双击事件
    jsPlumb.bind("dblclick", function (conn, originalEvent) {

        layer.confirm('确定删除此连线吗', {
            btn: ['确定','取消'] //按钮
        }, function(index){
            jsPlumb.detach(conn);
            layer.close(layer.index);
        }, function(){

        });
    });

    // 当连线建立前
    jsPlumb.bind('beforeDrop', function (info) {
        if(info.sourceId==info.targetId){//判断当开始和终点为一个节点时，不连线。
            return false
        }

        // var ojson = jsplumb_rule_expression_data();
        // //校验是否已经存在父节点
        // var line = ojson.line;
        // for (var i=0;i<line.length;i++){
        //     if(line[i].pageTargetId == info.targetId){
        //         layer.msg("只能有一个父节点,请重新选择连线");
        //         return false;
        //     }
        // }

        console.info("链接自动建立");
        return true // 链接会自动建立
    });

});

//双击节点内容区域时的事件
function doubleclick(id,tp) {
    switch (tp) {
        case "label"://服务器
            doubleclick_label(id);
            break;
        case "crowd_file":
            doubleclick_crowd_file(id);
            break;
        case "crowd_rule":
            doubleclick_crowd_rule(id);
            break;
        case "crowd_operate":
            doubleclick_crowd_operate(id);
            break;
        case "filter":
            doubleclick_filter(id);
            break;
        case "shunt":
            doubleclick_shunt(id);
            break;
        case "rights":
            doubleclick_rights(id);
            break;
        case "touch":
            doubleclick_touch(id);
            break;
        case "plugin":
            doubleclick_plugin(id);
            break;
        case "data_node":
            doubleclick_online(id);
            break;
        case "id_mapping":
            doubleclick_id_mapping(id);
            break;
        case "risk":
            doubleclick_risk(id);
            break;
        case "tn":
            doubleclick_tn(id);
            break;
        case "manual_confirm":
            doubleclick_manual_confirm(id);
            break;
        case "code_block":
            doubleclick_code_block(id);
            break;
        case "custom_list":
            doubleclick_custom_list(id);
            break;
        case "function":
            doubleclick_function(id);
            break;
        default:
            $(id).dblclick(function () {layer.msg("不支持的组件");});

    }
}

function doubleclick_label(id) {
    $(id).dblclick(function () {
        $("#etl_task_text").val("");
        var text = $(this).text();
        var div = $(this);
        var rule_id=div.attr("rule_id");
        var url=server_context+'/rule_detail.html';
        if( div.attr("rule_id") == "" || div.attr("rule_id") == undefined ){
            url=url+"?rule_id=-1"
        }else{
            var more_task=div.attr("more_task");
            var depend_level = div.attr("depend_level");
            var time_out = div.attr("time_out");
            var operate = div.attr("operate");
            var touch_type = div.attr("touch_type");
            var is_base = div.attr("is_base");
            var is_disenable = div.attr("is_disenable");
            var data_status = div.attr("data_status");
            var label_use_type = div.attr("label_use_type");
            $("#rule_param").val(div.attr("rule_param"));
            url=url+"?rule_id="+rule_id+"&more_task="+more_task+"&depend_level="+depend_level +"&time_out="+time_out+"&operate="+operate+"&touch_type="+touch_type+"&is_base="+is_base+"&is_disenable="+is_disenable+"&data_status="+data_status+"&label_use_type="+label_use_type
        }
        layer.open({
            type: 2,
            area: ['100%', '100%'],
            fixed: false, //不固定
            maxmin: true,
            content: encodeURI(url),
            end: function () {
                console.info("index:doubleclick:"+$("#etl_task_text").val());
                if($("#etl_task_text").val()==""){
                    console.info("无修改-不更新");
                    return ;
                }
                var etl_task_info=JSON.parse($("#etl_task_text").val());
                div.attr("more_task",etl_task_info.more_task);
                div.attr("is_disenable",etl_task_info.is_disenable);
                div.attr("time_out",etl_task_info.time_out);
                div.attr("touch_type",etl_task_info.touch_type);
                div.attr("operate",etl_task_info.operate);
                div.attr("is_base",etl_task_info.is_base);
                div.attr("depend_level",etl_task_info.depend_level);
                div.attr("data_status",etl_task_info.data_status);

                div.attr("label_use_type",etl_task_info.label_use_type);
                div.attr("rule_id",etl_task_info.rule_id);
                div.attr("rule_context",etl_task_info.rule_context);
                div.attr("rule_param",etl_task_info.rule_param);
                div.attr("rule_expression_cn",etl_task_info.rule_expression_cn);
                //div.width(etl_task_info.etl_context.length*16)
                div.css("width","auto");
                div.css("height","auto");
                div.css("display","inline-block");
                div.css("*display","inline");
                div.css("*zoom","1");
                div.attr("title", etl_task_info.rule_expression_cn);
                div.html("("+ etl_task_info.operate +")"+etl_task_info.rule_context);
                set_new_title(div, div.attr("type"), etl_task_info);
                div.css('background', get_color_by_status(etl_task_info.is_disenable));
                //此处更新中文表达式
                //create_rule_expression_cn();
            }
        });
    });
}

function doubleclick_crowd_file(id) {
    $(id).dblclick(function () {
        $("#etl_task_text").val("");
        var crowd_file_context = $(this).text();
        var div = $(this);
        var crowd_file_context=div.attr("crowd_file_context");
        var crowd_file=div.attr("crowd_file");
        var more_task=div.attr("more_task");
        var operate=div.attr("operate");
        var url=server_context+'/crowd_file_detail.html';
        if( crowd_file == "" || crowd_file == undefined ){
            url=url+"?crowd_file=-1"
        }else{
            var depend_level = div.attr("depend_level");
            var time_out = div.attr("time_out");
            var touch_type = div.attr("touch_type");
            var is_base = div.attr("is_base");
            var is_disenable = div.attr("is_disenable");
            var data_status = div.attr("data_status");
            url=url+"?crowd_file_context="+crowd_file_context+"&depend_level="+depend_level +"&time_out="+time_out+"&crowd_file="+crowd_file+"&more_task="+more_task+"&operate="+operate+"&touch_type="+touch_type+"&is_base="+is_base+"&is_disenable="+is_disenable+"&data_status="+data_status
        }
        layer.open({
            type: 2,
            area: ['700px', '450px'],
            fixed: false, //不固定
            maxmin: true,
            content: encodeURI(url),
            end: function () {
                console.info("index:doubleclick:"+$("#etl_task_text").val());
                if($("#etl_task_text").val()==""){
                    console.info("无修改-不更新");
                    return ;
                }

                var etl_task_info=JSON.parse($("#etl_task_text").val());
                div.attr("more_task",etl_task_info.more_task);
                div.attr("is_disenable",etl_task_info.is_disenable);
                div.attr("time_out",etl_task_info.time_out);
                div.attr("touch_type",etl_task_info.touch_type);
                div.attr("operate",etl_task_info.operate);
                div.attr("is_base",etl_task_info.is_base);
                div.attr("depend_level",etl_task_info.depend_level);
                div.attr("data_status",etl_task_info.data_status);

                div.attr("crowd_file_context",etl_task_info.crowd_file_context);
                div.attr("crowd_file",etl_task_info.crowd_file);
                div.attr("rule_expression_cn",etl_task_info.rule_expression_cn);

                div.css("width","auto");
                div.css("display","inline-block");
                div.css("*display","inline");
                div.css("*zoom","1");
                div.html("("+ etl_task_info.operate +")"+etl_task_info.crowd_file_context);
                set_new_title(div, div.attr("type"), etl_task_info);
                div.css('background', get_color_by_status(etl_task_info.is_disenable));
            }
        });
    });
}

function doubleclick_crowd_rule(id) {
    $(id).dblclick(function () {
        $("#etl_task_text").val("");
        var crowd_rule_context = $(this).text();
        var div = $(this);
        var crowd_rule_context=div.attr("crowd_rule_context");
        var crowd_rule=div.attr("crowd_rule");
        var operate=div.attr("operate");
        var url=server_context+'/crowd_rule_detail.html';
        if( crowd_rule == "" || crowd_rule == undefined ){
            url=url+"?crowd_rule=-1"
        }else{
            var depend_level = div.attr("depend_level");
            var time_out = div.attr("time_out");
            var touch_type = div.attr("touch_type");
            var is_disenable = div.attr("is_disenable");
            var is_base = div.attr("is_base");
            var data_status = div.attr("data_status");
            url=url+"?crowd_rule_context="+crowd_rule_context+"&depend_level="+depend_level +"&time_out="+time_out+"&crowd_rule="+crowd_rule+"&operate="+operate+"&touch_type="+touch_type+"&is_base="+is_base+"&is_disenable="+is_disenable+"&data_status="+data_status
        }
        layer.open({
            type: 2,
            area: ['700px', '450px'],
            fixed: false, //不固定
            maxmin: true,
            content: encodeURI(url),
            end: function () {
                console.info("index:doubleclick:"+$("#etl_task_text").val());
                if($("#etl_task_text").val()==""){
                    console.info("无修改-不更新");
                    return ;
                }

                var etl_task_info=JSON.parse($("#etl_task_text").val());
                div.attr("more_task",etl_task_info.more_task);
                div.attr("is_disenable",etl_task_info.is_disenable);
                div.attr("time_out",etl_task_info.time_out);
                div.attr("touch_type",etl_task_info.touch_type);
                div.attr("operate",etl_task_info.operate);
                div.attr("is_base",etl_task_info.is_base);
                div.attr("depend_level",etl_task_info.depend_level);
                div.attr("data_status",etl_task_info.data_status);

                div.attr("crowd_rule_context",etl_task_info.crowd_rule_context);
                div.attr("crowd_rule",etl_task_info.crowd_rule);

                div.css("width","auto");
                div.css("display","inline-block");
                div.css("*display","inline");
                div.css("*zoom","1");
                div.html("("+ etl_task_info.operate +")"+etl_task_info.crowd_rule_context);
                set_new_title(div, div.attr("type"), etl_task_info);
                div.css('background', get_color_by_status(etl_task_info.is_disenable));
            }
        });
    });
}

function doubleclick_crowd_operate(id) {
    $(id).dblclick(function () {
        $("#etl_task_text").val("");
        var crowd_operate_context = $(this).text();
        var div = $(this);
        var crowd_operate_context=div.attr("crowd_operate_context");
        var operate=div.attr("operate");
        var more_task=div.attr("more_task");
        var url=server_context+'/crowd_operate_detail.html';
        if( operate == "" || operate == undefined ){
            url=url+"?crowd_operate=-1"
        }else{
            var depend_level = div.attr("depend_level");
            var time_out = div.attr("time_out");
            var operate = div.attr("operate");
            var touch_type = div.attr("touch_type");
            var is_base = div.attr("is_base");
            var is_disenable = div.attr("is_disenable");
            var data_status = div.attr("data_status");
            url=url+"?crowd_operate_context="+crowd_operate_context+"&depend_level="+depend_level +"&time_out="+time_out+"&more_task="+more_task+"&operate="+operate+"&touch_type="+touch_type+"&is_base="+is_base+"&is_disenable="+is_disenable+"&data_status="+data_status
        }
        layer.open({
            type: 2,
            area: ['700px', '450px'],
            fixed: false, //不固定
            maxmin: true,
            content: encodeURI(url),
            end: function () {
                console.info("index:doubleclick:"+$("#etl_task_text").val());
                if($("#etl_task_text").val()==""){
                    console.info("无修改-不更新");
                    return ;
                }

                var etl_task_info=JSON.parse($("#etl_task_text").val());
                div.attr("more_task",etl_task_info.more_task);
                div.attr("is_disenable",etl_task_info.is_disenable);
                div.attr("time_out",etl_task_info.time_out);
                div.attr("touch_type",etl_task_info.touch_type);
                div.attr("operate",etl_task_info.operate);
                div.attr("is_base",etl_task_info.is_base);
                div.attr("depend_level",etl_task_info.depend_level);
                div.attr("data_status",etl_task_info.data_status);

                div.attr("crowd_operate_context",etl_task_info.crowd_operate_context);


                div.css("width","auto");
                div.css("display","inline-block");
                div.css("*display","inline");
                div.css("*zoom","1");
                div.html("("+ etl_task_info.operate +")"+etl_task_info.crowd_operate_context);
                set_new_title(div, div.attr("type"), etl_task_info);
                div.css('background', get_color_by_status(etl_task_info.is_disenable));
                //create_rule_expression_cn();
            }
        });
    });
}

function doubleclick_filter(id) {
    $(id).dblclick(function () {
        $("#etl_task_text").val("");

        var div = $(this);
        var rule_context=div.attr("rule_context");
        var filter=div.attr("filter");
        var operate=div.attr("operate");
        var url=server_context+'/filter_detail.html';
        if( filter == "" || filter == undefined ){
            url=url+"?filter=-1"
        }else{
            var depend_level = div.attr("depend_level");
            var time_out = div.attr("time_out");
            var touch_type = div.attr("touch_type");
            var is_disenable = div.attr("is_disenable");
            var is_base = div.attr("is_base");
            var data_status = div.attr("data_status");
            url=url+"?rule_context="+rule_context+"&depend_level="+depend_level +"&time_out="+time_out+"&filter="+filter+"&operate="+operate+"&touch_type="+touch_type+"&is_base="+is_base+"&is_disenable="+is_disenable+"&data_status="+data_status
        }
        layer.open({
            type: 2,
            area: ['700px', '450px'],
            fixed: false, //不固定
            maxmin: true,
            content: encodeURI(url),
            end: function () {
                console.info("index:doubleclick:"+$("#etl_task_text").val());
                if($("#etl_task_text").val()==""){
                    console.info("无修改-不更新");
                    return ;
                }

                var etl_task_info=JSON.parse($("#etl_task_text").val());
                div.attr("more_task",etl_task_info.more_task);
                div.attr("is_disenable",etl_task_info.is_disenable);
                div.attr("time_out",etl_task_info.time_out);
                div.attr("touch_type",etl_task_info.touch_type);
                div.attr("operate",etl_task_info.operate);
                div.attr("is_base",etl_task_info.is_base);
                div.attr("depend_level",etl_task_info.depend_level);
                div.attr("data_status",etl_task_info.data_status);

                div.attr("rule_context",etl_task_info.rule_context);
                div.attr("filter_title",etl_task_info.filter_title);
                div.attr("filter",etl_task_info.filter);

                div.css("width","auto");
                div.css("display","inline-block");
                div.css("*display","inline");
                div.css("*zoom","1");
                div.html("("+ etl_task_info.operate +")"+etl_task_info.rule_context);
                set_new_title(div, div.attr("type"), etl_task_info);
                div.css('background', get_color_by_status(etl_task_info.is_disenable));
            }
        });
    });
}

function doubleclick_shunt(id) {
    $(id).dblclick(function () {
        $("#etl_task_text").val("");
        var div = $(this);
        var rule_context=div.attr("rule_context");
        var shunt=div.attr("shunt");
        var shunt_param=div.attr("shunt_param");
        var operate=div.attr("operate");
        var url=server_context+'/shunt_detail.html';
        if( shunt == "" || shunt == undefined ){
            url=url+"?shunt=-1"
        }else{
            var depend_level = div.attr("depend_level");
            var time_out = div.attr("time_out");
            var touch_type = div.attr("touch_type");
            var is_disenable = div.attr("is_disenable");
            var is_base = div.attr("is_base");
            var data_status = div.attr("data_status");
            $('#shunt_param').val(shunt_param);
            url=url+"?rule_context="+rule_context+"&depend_level="+depend_level +"&time_out="+time_out+"&operate="+operate+"&shunt="+shunt+"&touch_type="+touch_type+"&is_base="+is_base+"&is_disenable="+is_disenable+"&data_status="+data_status
        }
        layer.open({
            type: 2,
            area: ['700px', '450px'],
            fixed: false, //不固定
            maxmin: true,
            content: encodeURI(url),
            end: function () {
                console.info("index:doubleclick:"+$("#etl_task_text").val());
                if($("#etl_task_text").val()==""){
                    console.info("无修改-不更新");
                    return ;
                }

                var etl_task_info=JSON.parse($("#etl_task_text").val());
                div.attr("more_task",etl_task_info.more_task);
                div.attr("is_disenable",etl_task_info.is_disenable);
                div.attr("time_out",etl_task_info.time_out);
                div.attr("touch_type",etl_task_info.touch_type);
                div.attr("operate",etl_task_info.operate);
                div.attr("is_base",etl_task_info.is_base);
                div.attr("depend_level",etl_task_info.depend_level);
                div.attr("data_status",etl_task_info.data_status);

                div.attr("shunt_param",etl_task_info.shunt_param);
                div.attr("rule_context",etl_task_info.rule_context);
                div.attr("shunt",etl_task_info.shunt);

                div.css("width","auto");
                div.css("display","inline-block");
                div.css("*display","inline");
                div.css("*zoom","1");
                div.html("("+ etl_task_info.operate +")"+etl_task_info.rule_context);
                set_new_title(div, div.attr("type"), etl_task_info);
                div.css('background', get_color_by_status(etl_task_info.is_disenable));
            }
        });
    });
}

function doubleclick_rights(id) {
    $(id).dblclick(function () {
        $("#etl_task_text").val("");
        var rights_context = $(this).text();
        var div = $(this);
        var rights_context=div.attr("rights_context");
        var rights=div.attr("rights");
        var rights_param=div.attr("rights_param");
        var url=server_context+'/rights_detail.html';
        if( rights == "" || rights == undefined ){
            url=url+"?rights=-1"
        }else{
            var depend_level = div.attr("depend_level");
            var time_out = div.attr("time_out");
            $('#rights_param').val(rights_param);
            var touch_type = div.attr("touch_type");
            var is_disenable = div.attr("is_disenable");
            var is_base = div.attr("is_base");
            var operate=div.attr("operate");
            var data_status = div.attr("data_status");
            url=url+"?rights_context="+rights_context+"&depend_level="+depend_level +"&time_out="+time_out+"&operate="+operate+"&rights="+rights+"&touch_type="+touch_type+"&is_base="+is_base+"&is_disenable="+is_disenable+"&data_status="+data_status
        }
        layer.open({
            type: 2,
            area: ['700px', '450px'],
            fixed: false, //不固定
            maxmin: true,
            content: encodeURI(url),
            end: function () {
                console.info("index:doubleclick:"+$("#etl_task_text").val());
                if($("#etl_task_text").val()==""){
                    console.info("无修改-不更新");
                    return ;
                }

                var etl_task_info=JSON.parse($("#etl_task_text").val());
                div.attr("more_task",etl_task_info.more_task);
                div.attr("is_disenable",etl_task_info.is_disenable);
                div.attr("time_out",etl_task_info.time_out);
                div.attr("touch_type",etl_task_info.touch_type);
                div.attr("operate",etl_task_info.operate);
                div.attr("is_base",etl_task_info.is_base);
                div.attr("depend_level",etl_task_info.depend_level);
                div.attr("data_status",etl_task_info.data_status);

                div.attr("rights_param",etl_task_info.rights_param);
                div.attr("rights_context",etl_task_info.rights_context);
                div.attr("rights",etl_task_info.rights);

                div.css("width","auto");
                div.css("display","inline-block");
                div.css("*display","inline");
                div.css("*zoom","1");
                div.html("("+ etl_task_info.operate +")"+etl_task_info.rights_context);
                set_new_title(div, div.attr("type"), etl_task_info);
                div.css('background', get_color_by_status(etl_task_info.is_disenable));
            }
        });
    });
}

function doubleclick_touch(id) {
    $(id).dblclick(function () {
        $("#etl_task_text").val("");
        var text = $(this).text();
        var div = $(this);
        var touch_id=div.attr("touch_id");
        var url=server_context+'/touch_detail.html';
        if( div.attr("touch_id") == "" || div.attr("touch_id") == undefined ){
            url=url+"?touch_id=-1"
        }else{
            var touch_task=div.attr("touch_task");
            var depend_level = div.attr("depend_level");
            var time_out = div.attr("time_out");
            var operate = div.attr("operate");
            var touch_type = div.attr("touch_type");
            var is_base = div.attr("is_base");
            var is_disenable = div.attr("is_disenable");
            var data_status = div.attr("data_status");
            var rule_context = div.attr("rule_context");
            url=url+"?touch_id="+touch_id+"&rule_context="+rule_context+"&touch_task="+touch_task+"&depend_level="+depend_level +"&time_out="+time_out+"&operate="+operate+"&touch_type="+touch_type+"&is_base="+is_base+"&is_disenable="+is_disenable+"&data_status="+data_status
        }
        layer.open({
            type: 2,
            area: ['100%', '100%'],
            fixed: false, //不固定
            maxmin: true,
            content: encodeURI(url),
            end: function () {
                console.info("index:doubleclick:"+$("#etl_task_text").val());
                if($("#etl_task_text").val()==""){
                    console.info("无修改-不更新");
                    return ;
                }
                var etl_task_info=JSON.parse($("#etl_task_text").val());
                div.attr("more_task",etl_task_info.more_task);
                div.attr("is_disenable",etl_task_info.is_disenable);
                div.attr("time_out",etl_task_info.time_out);
                div.attr("touch_type",etl_task_info.touch_type);
                div.attr("operate",etl_task_info.operate);
                div.attr("is_base",etl_task_info.is_base);
                div.attr("depend_level",etl_task_info.depend_level);
                div.attr("data_status",etl_task_info.data_status);

                div.attr("rule_context",etl_task_info.rule_context);
                div.attr("touch_task",etl_task_info.touch_task);
                div.attr("touch_id",etl_task_info.touch_id);
                div.attr("touch_context",etl_task_info.touch_context);
                div.attr("touch_param",etl_task_info.touch_param);
                //div.width(etl_task_info.etl_context.length*16)
                div.css("width","auto");
                div.css("height","auto");
                div.css("display","inline-block");
                div.css("*display","inline");
                div.css("*zoom","1");
                div.html("("+ etl_task_info.operate +")"+etl_task_info.rule_context);
                set_new_title(div, div.attr("type"), etl_task_info);
                div.css('background', get_color_by_status(etl_task_info.is_disenable));
            }
        });
    });
}

function doubleclick_plugin(id) {
    $(id).dblclick(function () {
        $("#etl_task_text").val("");
        var text = $(this).text();
        var div = $(this);
        var rule_id=div.attr("rule_id");
        var rule_context=div.attr("rule_context");
        var url=server_context+'/plugin_detail2.html';
        if( div.attr("rule_id") == "" || div.attr("rule_id") == undefined ){
            url=url+"?rule_id=-1"
        }else{
            var more_task=div.attr("more_task");
            var depend_level = div.attr("depend_level");
            var time_out = div.attr("time_out");
            var operate = div.attr("operate");
            var touch_type = div.attr("touch_type");
            var is_base = div.attr("is_base");
            var is_disenable = div.attr("is_disenable");
            var data_status = div.attr("data_status");
            $("#rule_param").val(div.attr("rule_param"));
            url=url+"?rule_id="+rule_id+"&rule_context="+rule_context+"&more_task="+more_task+"&depend_level="+depend_level +"&time_out="+time_out+"&operate="+operate+"&touch_type="+touch_type+"&is_base="+is_base+"&is_disenable="+is_disenable+"&data_status="+data_status
        }
        layer.open({
            type: 2,
            area: ['100%', '100%'],
            fixed: false, //不固定
            maxmin: true,
            content: encodeURI(url),
            end: function () {
                console.info("index:doubleclick:"+$("#etl_task_text").val());
                if($("#etl_task_text").val()==""){
                    console.info("无修改-不更新");
                    return ;
                }
                var etl_task_info=JSON.parse($("#etl_task_text").val());
                div.attr("more_task",etl_task_info.more_task);
                div.attr("is_disenable",etl_task_info.is_disenable);
                div.attr("time_out",etl_task_info.time_out);
                div.attr("touch_type",etl_task_info.touch_type);
                div.attr("operate",etl_task_info.operate);
                div.attr("is_base",etl_task_info.is_base);
                div.attr("depend_level",etl_task_info.depend_level);
                div.attr("data_status",etl_task_info.data_status);

                div.attr("rule_id",etl_task_info.rule_id);
                div.attr("rule_context",etl_task_info.rule_context);
                div.attr("rule_param",etl_task_info.rule_param);
                div.attr("rule_expression_cn",etl_task_info.rule_expression_cn);
                //div.width(etl_task_info.etl_context.length*16)
                div.css("width","auto");
                div.css("height","auto");
                div.css("display","inline-block");
                div.css("*display","inline");
                div.css("*zoom","1");
                div.attr("title", etl_task_info.rule_expression_cn);
                div.html("("+ etl_task_info.operate +")"+etl_task_info.rule_context);
                set_new_title(div, div.attr("type"), etl_task_info);
                div.css('background', get_color_by_status(etl_task_info.is_disenable));
                //此处更新中文表达式
                //create_rule_expression_cn();
            }
        });
    });
}

function doubleclick_online(id) {
    $(id).dblclick(function () {
        $("#etl_task_text").val("");
        var text = $(this).text();
        var div = $(this);
        var data_node=div.attr("data_node");
        var url=server_context+'/online_detail.html';
        if( div.attr("data_node") == "" || div.attr("data_node") == undefined ){
            url=url+"?data_node=-1"
        }else{
            var more_task=div.attr("more_task");
            var depend_level = div.attr("depend_level");
            var time_out = div.attr("time_out");
            var operate = div.attr("operate");
            var touch_type = div.attr("touch_type");
            var is_base = div.attr("is_base");
            var is_disenable = div.attr("is_disenable");
            var data_node = div.attr("data_node");
            var rule_context = div.attr("rule_context");
            var data_status = div.attr("data_status");
            url=url+"?data_node="+data_node+"&more_task="+more_task+"&depend_level="+depend_level +"&time_out="+time_out+"&operate="+operate+"&touch_type="+touch_type+"&is_base="+is_base+"&is_disenable="+is_disenable+"&rule_context="+rule_context+"&data_status="+data_status
        }
        layer.open({
            type: 2,
            area: ['100%', '100%'],
            fixed: false, //不固定
            maxmin: true,
            content: encodeURI(url),
            end: function () {
                console.info("index:doubleclick:"+$("#etl_task_text").val());
                if($("#etl_task_text").val()==""){
                    console.info("无修改-不更新");
                    return ;
                }
                var etl_task_info=JSON.parse($("#etl_task_text").val());
                div.attr("more_task",etl_task_info.more_task);
                div.attr("is_disenable",etl_task_info.is_disenable);
                div.attr("time_out",etl_task_info.time_out);
                div.attr("touch_type",etl_task_info.touch_type);
                div.attr("operate",etl_task_info.operate);
                div.attr("is_base",etl_task_info.is_base);
                div.attr("depend_level",etl_task_info.depend_level);
                div.attr("data_status",etl_task_info.data_status);

                div.attr("data_node",etl_task_info.data_node);
                div.attr("rule_context",etl_task_info.rule_context);
                //div.width(etl_task_info.etl_context.length*16)
                div.css("width","auto");
                div.css("height","auto");
                div.css("display","inline-block");
                div.css("*display","inline");
                div.css("*zoom","1");
                div.attr("title", etl_task_info.rule_context);
                div.html("("+ etl_task_info.operate +")"+etl_task_info.rule_context);
                set_new_title(div, div.attr("type"), etl_task_info);
                div.css('background', get_color_by_status(etl_task_info.is_disenable));
                //此处更新中文表达式
                //create_rule_expression_cn();
            }
        });
    });
}

function doubleclick_id_mapping(id) {
    $(id).dblclick(function () {
        $("#etl_task_text").val("");
        var text = $(this).text();
        var div = $(this);
        var data_node=div.attr("data_node");
        var url=server_context+'/id_mapping_detail.html';
        if( div.attr("id_mapping_code") == "" || div.attr("id_mapping_code") == undefined ){
            url=url+"?id_mapping_code=-1"
        }else{
            var more_task=div.attr("more_task");
            var depend_level = div.attr("depend_level");
            var time_out = div.attr("time_out");
            var operate = div.attr("operate");
            var touch_type = div.attr("touch_type");
            var is_base = div.attr("is_base");
            var is_disenable = div.attr("is_disenable");
            var id_mapping_code = div.attr("id_mapping_code");
            var data_engine = div.attr("data_engine");
            var rule_context = div.attr("rule_context");
            var data_status = div.attr("data_status");
            url=url+"?id_mapping_code="+id_mapping_code+"&more_task="+more_task+"&depend_level="+depend_level +"&time_out="+time_out+"&operate="+operate+"&touch_type="+touch_type+"&is_base="+is_base+"&is_disenable="+is_disenable+"&rule_context="+rule_context+"&data_engine="+data_engine+"&data_status="+data_status
        }
        layer.open({
            type: 2,
            area: ['100%', '100%'],
            fixed: false, //不固定
            maxmin: true,
            content: encodeURI(url),
            end: function () {
                console.info("index:doubleclick:"+$("#etl_task_text").val());
                if($("#etl_task_text").val()==""){
                    console.info("无修改-不更新");
                    return ;
                }
                var etl_task_info=JSON.parse($("#etl_task_text").val());
                div.attr("more_task",etl_task_info.more_task);
                div.attr("is_disenable",etl_task_info.is_disenable);
                div.attr("time_out",etl_task_info.time_out);
                div.attr("touch_type",etl_task_info.touch_type);
                div.attr("operate",etl_task_info.operate);
                div.attr("is_base",etl_task_info.is_base);
                div.attr("depend_level",etl_task_info.depend_level);
                div.attr("data_status",etl_task_info.data_status);

                div.attr("id_mapping_code",etl_task_info.id_mapping_code);
                div.attr("data_engine",etl_task_info.data_engine);
                div.attr("rule_context",etl_task_info.rule_context);
                //div.width(etl_task_info.etl_context.length*16)
                div.css("width","auto");
                div.css("height","auto");
                div.css("display","inline-block");
                div.css("*display","inline");
                div.css("*zoom","1");
                div.attr("title", etl_task_info.rule_context);
                div.html("("+ etl_task_info.operate +")"+etl_task_info.rule_context);
                set_new_title(div, div.attr("type"), etl_task_info);
                div.css('background', get_color_by_status(etl_task_info.is_disenable));
                //此处更新中文表达式
                //create_rule_expression_cn();
            }
        });
    });
}

function doubleclick_risk(id) {
    $(id).dblclick(function () {
        $("#etl_task_text").val("");
        var text = $(this).text();
        var div = $(this);
        var rule_id=div.attr("rule_id");
        var url=server_context+'/risk_event_detail2.html';
        if( div.attr("rule_id") == "" || div.attr("rule_id") == undefined ){
            url=url+"?rule_id=-1"
        }else{
            var more_task=div.attr("more_task");
            var depend_level = div.attr("depend_level");
            var time_out = div.attr("time_out");
            var operate = div.attr("operate");
            var touch_type = div.attr("touch_type");
            var is_base = div.attr("is_base");
            var is_disenable = div.attr("is_disenable");
            var data_status = div.attr("data_status");
            $("#rule_param").val(div.attr("rule_param"));
            url=url+"?rule_id="+rule_id+"&more_task="+more_task+"&depend_level="+depend_level +"&time_out="+time_out+"&operate="+operate+"&touch_type="+touch_type+"&is_base="+is_base+"&is_disenable="+is_disenable+"&data_status="+data_status
        }
        layer.open({
            type: 2,
            area: ['100%', '100%'],
            fixed: false, //不固定
            maxmin: true,
            content: encodeURI(url),
            end: function () {
                console.info("index:doubleclick:"+$("#etl_task_text").val());
                if($("#etl_task_text").val()==""){
                    console.info("无修改-不更新");
                    return ;
                }
                var etl_task_info=JSON.parse($("#etl_task_text").val());
                div.attr("more_task",etl_task_info.more_task);
                div.attr("is_disenable",etl_task_info.is_disenable);
                div.attr("time_out",etl_task_info.time_out);
                div.attr("touch_type",etl_task_info.touch_type);
                div.attr("operate",etl_task_info.operate);
                div.attr("is_base",etl_task_info.is_base);
                div.attr("depend_level",etl_task_info.depend_level);
                div.attr("data_status",etl_task_info.data_status);

                div.attr("rule_id",etl_task_info.rule_id);
                div.attr("rule_context",etl_task_info.rule_context);
                div.attr("rule_param",etl_task_info.rule_param);
                div.attr("rule_expression_cn",etl_task_info.rule_expression_cn);
                //div.width(etl_task_info.etl_context.length*16)
                div.css("width","auto");
                div.css("height","auto");
                div.css("display","inline-block");
                div.css("*display","inline");
                div.css("*zoom","1");
                div.attr("title", etl_task_info.rule_expression_cn);
                div.html("("+ etl_task_info.operate +")"+etl_task_info.rule_context);
                set_new_title(div, div.attr("type"), etl_task_info);
                div.css('background', get_color_by_status(etl_task_info.is_disenable));
                //此处更新中文表达式
                //create_rule_expression_cn();
            }
        });
    });
}

function doubleclick_tn(id) {
    $(id).dblclick(function () {
        $("#etl_task_text").val("");
        var text = $(this).text();
        var div = $(this);
        var rule_id=div.attr("rule_id");
        var rule_context=div.attr("rule_context");
        var url=server_context+'/tn_detail.html';
        if( div.attr("rule_id") == "" || div.attr("rule_id") == undefined ){
            url=url+"?rule_id=-1"
        }else{
            var tn_unit=div.attr("tn_unit");
            var tn_type=div.attr("tn_type");
            var tn_value=div.attr("tn_value");
            var depend_level = div.attr("depend_level");
            var time_out = div.attr("time_out");
            var operate = div.attr("operate");
            var touch_type = div.attr("touch_type");
            var is_base = div.attr("is_base");
            var is_disenable = div.attr("is_disenable");
            var data_status = div.attr("data_status");
            url=url+"?rule_id="+rule_id+"&rule_context="+rule_context+"&tn_unit="+tn_unit+"&tn_type="+tn_type+"&tn_value="+tn_value+"&depend_level="+depend_level +"&time_out="+time_out+"&operate="+operate+"&touch_type="+touch_type+"&is_base="+is_base+"&is_disenable="+is_disenable+"&data_status="+data_status
        }
        layer.open({
            type: 2,
            area: ['100%', '100%'],
            fixed: false, //不固定
            maxmin: true,
            content: encodeURI(url),
            end: function () {
                console.info("index:doubleclick:"+$("#etl_task_text").val());
                if($("#etl_task_text").val()==""){
                    console.info("无修改-不更新");
                    return ;
                }
                var etl_task_info=JSON.parse($("#etl_task_text").val());
                div.attr("more_task",etl_task_info.more_task);
                div.attr("is_disenable",etl_task_info.is_disenable);
                div.attr("time_out",etl_task_info.time_out);
                div.attr("touch_type",etl_task_info.touch_type);
                div.attr("operate",etl_task_info.operate);
                div.attr("is_base",etl_task_info.is_base);
                div.attr("depend_level",etl_task_info.depend_level);
                div.attr("data_status",etl_task_info.data_status);

                div.attr("tn_unit",etl_task_info.tn_unit);
                div.attr("tn_type",etl_task_info.tn_type);
                div.attr("tn_value",etl_task_info.tn_value);
                div.attr("rule_id",etl_task_info.rule_id);
                div.attr("rule_context",etl_task_info.rule_context);
                //div.width(etl_task_info.etl_context.length*16)
                div.css("width","auto");
                div.css("height","auto");
                div.css("display","inline-block");
                div.css("*display","inline");
                div.css("*zoom","1");
                div.attr("title", etl_task_info.rule_context);
                div.html("("+ etl_task_info.operate +")"+etl_task_info.rule_context);
                set_new_title(div, div.attr("type"), etl_task_info);
                div.css('background', get_color_by_status(etl_task_info.is_disenable));
            }
        });
    });
}

function doubleclick_manual_confirm(id) {
    $(id).dblclick(function () {
        $("#etl_task_text").val("");
        var rule_context = $(this).text();
        var div = $(this);
        var rule_context=div.attr("rule_context");
        var rule_id=div.attr("rule_id");
        var operate=div.attr("operate");
        var url=server_context+'/manual_confirm_detail.html';
        if( rule_id == "" || rule_id == undefined ){
            url=url+"?rule_id=-1"
        }else{
            var depend_level = div.attr("depend_level");
            var time_out = div.attr("time_out");
            var touch_type = div.attr("touch_type");
            var is_disenable = div.attr("is_disenable");
            var is_base = div.attr("is_base");
            var confirm_notice_type=div.attr("confirm_notice_type");
            var data_status = div.attr("data_status");
            url=url+"?rule_context="+rule_context+"&depend_level="+depend_level +"&time_out="+time_out+"&rule_id="+rule_id+"&confirm_notice_type="+confirm_notice_type+"&operate="+operate+"&touch_type="+touch_type+"&is_base="+is_base+"&is_disenable="+is_disenable+"&data_status="+data_status
        }
        layer.open({
            type: 2,
            area: ['700px', '450px'],
            fixed: false, //不固定
            maxmin: true,
            content: encodeURI(url),
            end: function () {
                console.info("index:doubleclick:"+$("#etl_task_text").val());
                if($("#etl_task_text").val()==""){
                    console.info("无修改-不更新");
                    return ;
                }

                var etl_task_info=JSON.parse($("#etl_task_text").val());
                div.attr("more_task",etl_task_info.more_task);
                div.attr("is_disenable",etl_task_info.is_disenable);
                div.attr("time_out",etl_task_info.time_out);
                div.attr("touch_type",etl_task_info.touch_type);
                div.attr("operate",etl_task_info.operate);
                div.attr("is_base",etl_task_info.is_base);
                div.attr("depend_level",etl_task_info.depend_level);
                div.attr("data_status",etl_task_info.data_status);

                div.attr("rule_context",etl_task_info.rule_context);
                div.attr("rule_id",etl_task_info.rule_id);
                div.attr("confirm_notice_type",etl_task_info.confirm_notice_type);

                div.css("width","auto");
                div.css("display","inline-block");
                div.css("*display","inline");
                div.css("*zoom","1");
                div.html("("+ etl_task_info.operate +")"+etl_task_info.rule_context);
                set_new_title(div, div.attr("type"), etl_task_info);
                div.css('background', get_color_by_status(etl_task_info.is_disenable));
            }
        });
    });
}

function doubleclick_code_block(id) {
    $(id).dblclick(function () {
        $("#etl_task_text").val("");
        var rule_context = $(this).text();
        var div = $(this);
        var rule_context=div.attr("rule_context");
        var rule_id=div.attr("rule_id");
        var operate=div.attr("operate");
        var command=div.attr("command");
        var url=server_context+'/code_block_detail.html';
        if( rule_id == "" || rule_id == undefined ){
            url=url+"?rule_id=-1"
        }else{
            var depend_level = div.attr("depend_level");
            var time_out = div.attr("time_out");
            var touch_type = div.attr("touch_type");
            var is_disenable = div.attr("is_disenable");
            var is_base = div.attr("is_base");
            var code_type=div.attr("code_type");
            var data_status = div.attr("data_status");
            $("#code_text").val(command);
            url=url+"?rule_context="+rule_context+"&depend_level="+depend_level +"&time_out="+time_out+"&rule_id="+rule_id+"&code_type="+code_type+"&operate="+operate+"&touch_type="+touch_type+"&is_base="+is_base+"&is_disenable="+is_disenable+"&data_status="+data_status
        }
        layer.open({
            type: 2,
            area: ['700px', '450px'],
            fixed: false, //不固定
            maxmin: true,
            content: encodeURI(url),
            end: function () {
                console.info("index:doubleclick:"+$("#etl_task_text").val());
                if($("#etl_task_text").val()==""){
                    console.info("无修改-不更新");
                    return ;
                }

                var etl_task_info=JSON.parse($("#etl_task_text").val());
                div.attr("more_task",etl_task_info.more_task);
                div.attr("is_disenable",etl_task_info.is_disenable);
                div.attr("time_out",etl_task_info.time_out);
                div.attr("touch_type",etl_task_info.touch_type);
                div.attr("operate",etl_task_info.operate);
                div.attr("is_base",etl_task_info.is_base);
                div.attr("depend_level",etl_task_info.depend_level);
                div.attr("data_status",etl_task_info.data_status);

                div.attr("rule_context",etl_task_info.rule_context);
                div.attr("rule_id",etl_task_info.rule_id);
                div.attr("code_type",etl_task_info.code_type);
                div.attr("command",etl_task_info.command);

                div.css("width","auto");
                div.css("display","inline-block");
                div.css("*display","inline");
                div.css("*zoom","1");
                div.html("("+ etl_task_info.operate +")"+etl_task_info.rule_context);
                set_new_title(div, div.attr("type"), etl_task_info);
                div.css('background', get_color_by_status(etl_task_info.is_disenable));
            }
        });
    });
}

function doubleclick_custom_list(id) {
    $(id).dblclick(function () {
        $("#etl_task_text").val("");
        var rule_context = $(this).text();
        var div = $(this);
        var rule_context=div.attr("rule_context");
        var rule_id=div.attr("rule_id");
        var operate=div.attr("operate");
        var name_list=div.attr("name_list");
        var url=server_context+'/custom_list_detail.html';
        if( rule_id == "" || rule_id == undefined ){
            url=url+"?rule_id=-1"
        }else{
            var depend_level = div.attr("depend_level");
            var time_out = div.attr("time_out");
            var touch_type = div.attr("touch_type");
            var is_disenable = div.attr("is_disenable");
            var is_base = div.attr("is_base");
            var code_type=div.attr("code_type");
            var data_status = div.attr("data_status");
            $("#code_text").val(name_list);
            url=url+"?rule_context="+rule_context+"&depend_level="+depend_level +"&time_out="+time_out+"&rule_id="+rule_id+"&code_type="+code_type+"&operate="+operate+"&touch_type="+touch_type+"&is_base="+is_base+"&is_disenable="+is_disenable+"&data_status="+data_status
        }
        layer.open({
            type: 2,
            area: ['700px', '450px'],
            fixed: false, //不固定
            maxmin: true,
            content: encodeURI(url),
            end: function () {
                console.info("index:doubleclick:"+$("#etl_task_text").val());
                if($("#etl_task_text").val()==""){
                    console.info("无修改-不更新");
                    return ;
                }

                var etl_task_info=JSON.parse($("#etl_task_text").val());
                div.attr("more_task",etl_task_info.more_task);
                div.attr("is_disenable",etl_task_info.is_disenable);
                div.attr("time_out",etl_task_info.time_out);
                div.attr("touch_type",etl_task_info.touch_type);
                div.attr("operate",etl_task_info.operate);
                div.attr("is_base",etl_task_info.is_base);
                div.attr("depend_level",etl_task_info.depend_level);
                div.attr("data_status",etl_task_info.data_status);

                div.attr("rule_context",etl_task_info.rule_context);
                div.attr("rule_id",etl_task_info.rule_id);
                div.attr("name_list",etl_task_info.name_list);

                div.css("width","auto");
                div.css("display","inline-block");
                div.css("*display","inline");
                div.css("*zoom","1");
                div.html("("+ etl_task_info.operate +")"+etl_task_info.rule_context);
                div.attr("title", etl_task_info.rule_context);
                set_new_title(div, div.attr("type"), etl_task_info);
                div.css('background', get_color_by_status(etl_task_info.is_disenable));
            }
        });
    });
}

function doubleclick_function(id) {
    $(id).dblclick(function () {
        $("#etl_task_text").val("");

        var div = $(this);
        var rule_context=div.attr("rule_context");
        var rule_id=div.attr("rule_id");
        var operate=div.attr("operate");
        var url=server_context+'/function_detail2.html';
        if( rule_id == "" || rule_id == undefined ){
            url=url+"?rule_id=-1"
        }else{
            var depend_level = div.attr("depend_level");
            var time_out = div.attr("time_out");
            var touch_type = div.attr("touch_type");
            var is_disenable = div.attr("is_disenable");
            var is_base = div.attr("is_base");
            var data_status = div.attr("data_status");

            var return_diff_enable = div.attr("return_diff_enable");
            var return_value_type = div.attr("return_value_type");
            var return_value_express = div.attr("return_value_express");
            var return_operate = div.attr("return_operate");
            var return_operate_value = div.attr("return_operate_value");

            $("#rule_param").val(div.attr("rule_param"));
            url=url+"?rule_context="+rule_context+"&depend_level="+depend_level +"&time_out="+time_out+"&rule_id="+rule_id+"&operate="+operate+"&touch_type="+touch_type+"&is_base="+is_base+"&is_disenable="+is_disenable+"&data_status="+data_status+
                '&return_diff_enable='+return_diff_enable+
                '&return_value_type='+return_value_type+
                '&return_value_express='+return_value_express+
                '&return_operate='+return_operate+
                '&return_operate_value='+return_operate_value
        }
        layer.open({
            type: 2,
            area: ['700px', '450px'],
            fixed: false, //不固定
            maxmin: true,
            content: encodeURI(url),
            end: function () {
                console.info("index:doubleclick:"+$("#etl_task_text").val());
                if($("#etl_task_text").val()==""){
                    console.info("无修改-不更新");
                    return ;
                }

                var etl_task_info=JSON.parse($("#etl_task_text").val());
                div.attr("more_task",etl_task_info.more_task);
                div.attr("is_disenable",etl_task_info.is_disenable);
                div.attr("time_out",etl_task_info.time_out);
                div.attr("touch_type",etl_task_info.touch_type);
                div.attr("operate",etl_task_info.operate);
                div.attr("is_base",etl_task_info.is_base);
                div.attr("depend_level",etl_task_info.depend_level);
                div.attr("data_status",etl_task_info.data_status);

                div.attr("return_diff_enable",etl_task_info.return_diff_enable);
                div.attr("return_value_type",etl_task_info.return_value_type);
                div.attr("return_value_express",etl_task_info.return_value_express);
                div.attr("return_operate",etl_task_info.return_operate);
                div.attr("return_operate_value",etl_task_info.return_operate_value);

                div.attr("rule_context",etl_task_info.rule_context);
                div.attr("rule_id",etl_task_info.rule_id);
                div.attr("rule_param",etl_task_info.rule_param);

                div.css("width","auto");
                div.css("display","inline-block");
                div.css("*display","inline");
                div.css("*zoom","1");
                div.html("("+ etl_task_info.operate +")"+etl_task_info.rule_context);
                div.attr("title", etl_task_info.rule_context);
                set_new_title(div, div.attr("type"), etl_task_info);
                div.css('background', get_color_by_status(etl_task_info.is_disenable));
            }
        });
    });
}

function create_rule_expression_cn(){
    console.info("开始生成中文表达式");
    var ojson = jsplumb_rule_expression_data();
    var expression_cn = new Map();
    var operate = new Map();
    for (var i=0;i<ojson.tasks.length;i++){
        expression_cn.set(ojson.tasks[i].id, ojson.tasks[i].rule_expression_cn);
        operate.set(ojson.tasks[i].id, ojson.tasks[i].operate);
    }

    if(ojson.tasks.length==1){
        //只有一个节点
        $('#rule_expression_cn').val(ojson.tasks[0].rule_expression_cn);
        return ;
    }

    //确定根节点
    var chilren_parent = new Map();
    var parent_chilren = new Map();
    for (var i=0;i<ojson.line.length;i++){
        chilren_parent.set(ojson.line[i].pageTargetId, ojson.line[i].pageSourceId);
        parent_chilren.set(ojson.line[i].pageSourceId, ojson.line[i].pageTargetId);
    }
    var root_id = '';
    for (var i=0;i<ojson.line.length;i++){
        if(!chilren_parent.has(ojson.line[i].pageSourceId)){
            //没有父节点,表示是根节点
            root_id = ojson.line[i].pageSourceId;
            break ;
        }
    }

    if(root_id == ''){
        layer.msg("生成中文表达式异常,无法找到根节点");
        return ;
    }

    var str = rule_expression(root_id, root_id, expression_cn, operate, parent_chilren);
    $('#rule_expression_cn').val(str);
}


function rule_expression(root_id,source_id, expression_cn, operate, parent_chilren){

    var str = "";
    //节点有子节点
    if(parent_chilren.has(source_id)){
        if(root_id == source_id) {
            str = expression_cn.get(source_id)
        }
        var chilren_source_id = parent_chilren.get(source_id);
        str = str + " " + rule_expression(root_id, chilren_source_id, expression_cn, operate, parent_chilren);
    }else{
        if(root_id == source_id){
            str = expression_cn.get(source_id)
        }else{
            str = operate.get(source_id) + " " + expression_cn.get(source_id)
        }
    }

    return str ;

}


function jsplumb_rule_expression_data(){
    var ojson={
        tasks:[],
        line:[]
    };
    //服务器
    $("#m1 .tasks").each(function (idx, elem) {
        var $elem = $(elem);
        var param={
            id: $elem.attr('id'),
            rule_expression_cn: $elem.attr('rule_expression_cn'),
            operate: $elem.attr('operate'),
            type:$elem.data('type')
        };
        ojson.tasks.push(param)
    });

    $.each(jsPlumb.getConnections(), function (idx, connection) {
        var param={
            connectionId: connection.id,
            pageSourceId: connection.sourceId,
            pageTargetId: connection.targetId
        };
        ojson.line.push(param)
    });
    console.info(ojson);

    return ojson
}

function set_new_title(div, tp, rs){
    var new_title = "表达式: "+div.html();
    var more_task = "任务类型: "+tp;
    var chudashiji = "触发时机: 上游成功";
    var shujuzhuangtai = "依赖数据状态: 上游成功数据";
    if(!is_empty(rs.depend_level)){
        if(rs.depend_level == "1"){
            chudashiji = "触发时机: 上游杀死";
        }else if(rs.depend_level == "2"){
            chudashiji = "触发时机: 上游失败";
        }else if(rs.depend_level == "3"){
            chudashiji = "触发时机: 上游执行后";
        }
    }
    if(!is_empty(rs.data_status)){
        if(rs.data_status == "1"){
            shujuzhuangtai = "依赖数据状态: 上游成功数据";
        }else if(rs.data_status == "2"){
            shujuzhuangtai = "依赖数据状态: 上游失败数据";
        }else if(rs.data_status == "3"){
            shujuzhuangtai = "依赖数据状态: 上游所有数据";
        }
    }

    new_title = more_task + "\n" + chudashiji + "\n" + shujuzhuangtai+ "\n" + new_title;

    div.attr("title", new_title);
}
 

