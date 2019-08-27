/**
 * @author 光芒
 *
 * @requires jQuery
 *
 * 格式化日期时间
 */
function DateTimeFormatter(val) {
    var date=new Date(val);
    var year=date.getFullYear();
    var month=date.getMonth()+1;
    month=month>9?month:('0'+month);
    var day=date.getDate();
    day=day>9?day:('0'+day);
    var hh=date.getHours();
    hh=hh>9?hh:('0'+hh);
    var mm=date.getMinutes();
    mm=mm>9?mm:('0'+mm);
    var ss=date.getSeconds();
    ss=ss>9?ss:('0'+ss);
    var time=year+'-'+month+'-'+day+' '+hh+':'+mm+':'+ss;
    return time;
}


/**
 * 表格数据转化是否有效
 */
function isUse(val){
    if(0==val){
        return "<span style='color: red'>无效</span>"
    }else if(1==val){
        return "<span style='color: #0a0a0a'>有效</span>"
    }
}


/**
 * 格式化json字符串方便查看
 * @param json
 * @param options
 * @returns {string}
 */
function formatJson(json, options) {
    var reg = null,
        formatted = '',
        pad = 0,
        PADDING = '    '; // one can also use '\t' or a different number of spaces
    // optional settings
    options = options || {};
    // remove newline where '{' or '[' follows ':'
    options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
    // use a space after a colon
    options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;

    // begin formatting...

    // make sure we start with the JSON as a string
    if (typeof json !== 'string') {
        json = JSON.stringify(json);
    }
    // parse and stringify in order to remove extra whitespace
    json = JSON.parse(json);
    json = JSON.stringify(json);

    // add newline before and after curly braces
    reg = /([\{\}])/g;
    json = json.replace(reg, '\r\n$1\r\n');

    // add newline before and after square brackets
    reg = /([\[\]])/g;
    json = json.replace(reg, '\r\n$1\r\n');

    // add newline after comma
    reg = /(\,)/g;
    json = json.replace(reg, '$1\r\n');

    // remove multiple newlines
    reg = /(\r\n\r\n)/g;
    json = json.replace(reg, '\r\n');

    // remove newlines before commas
    reg = /\r\n\,/g;
    json = json.replace(reg, ',');

    // optional formatting...
    if (!options.newlineAfterColonIfBeforeBraceOrBracket) {
        reg = /\:\r\n\{/g;
        json = json.replace(reg, ':{');
        reg = /\:\r\n\[/g;
        json = json.replace(reg, ':[');
    }
    if (options.spaceAfterColon) {
        reg = /\:/g;
        json = json.replace(reg, ': ');
    }

    $.each(json.split('\r\n'), function(index, node) {
        var i = 0,
            indent = 0,
            padding = '';

        if (node.match(/\{$/) || node.match(/\[$/)) {
            indent = 1;
        } else if (node.match(/\}/) || node.match(/\]/)) {
            if (pad !== 0) {
                pad -= 1;
            }
        } else {
            indent = 0;
        }

        for (i = 0; i < pad; i++) {
            padding += PADDING;
        }

        formatted += padding + node + '\r\n';
        pad += indent;
    });

    return formatted;
};



/**
 * form表单转化为json参数
 */
$.fn.serializeObject = function(){
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

/**
 * 选中datagrid转json
 */
function selectionsToJson(selections) {
    var deleteObj = new Array();
    $.each(selections, function() {
        deleteObj.push({
            "id" : this.id,
            "updateDate" : this.updateDate,
            "isuse":this.isuse
        });
    });
    return deleteObj;
}

/**
 * 封装的 json 请求
 */
$.requestJson = function(uri,param, okCallback, failCallback){
    param = param ? param :{};
    console.info(JSON.stringify(param));
    $.ajax({
        url:uri,
        dataType:"json",
        type:"POST",
        data:JSON.stringify(param),
        contentType:"application/json; charset=utf-8",
        success:function(data, textStatus, jqXHR){
            okCallback(data, textStatus, jqXHR);
        },
        error:function(data, textStatus, errorThrown){
            $.messager.alert('Warning','发送请求失败');
            // failCallback(data, textStatus, errorThrown);
        }
    });
};

/**
 * form submit 提交，下载文件时提交
 *
 * options ：{}
 * 		   	属性：
 * 			url							地址
 * 			queryData		            可以在from参数的基础上再做处理
 * 			okCallback:function()
 * 										操作成功: JsonResult.isOk() == true
 * 			failCallback:function()
 * 										操作失败: JsonResult.isOk() == false
 */
function downloadSubmit(options){
    $('#downloadForm').form('submit', {
        url:options.url,
        onSubmit: function(param){
            if(options.queryData!=null){
                param = $.extend(param, options.queryData);
            }
        },
        success:function(data){
            // 反转义
            // data = HTMLDecode(data);
            // var dataObj = JSON.parse(data);
           // commonReqSuccessHandler(dataObj, options.okCallback, options.failCallback, true);
        }
    });
}

//HTML反转义
function HTMLDecode(text)
{
    var temp = document.createElement("div");
    temp.innerHTML = text;
    var output = temp.innerText || temp.textContent;
    temp = null;
    return output;
}


/**
 * 给详细添加链接
 */
function linkCellFormatter() {
    if(hasText(value)){
        return '<a class=\'table-link-cell\' field=\'' + this.field + '\' index=\''+index+'\' onclick=\'commonLinkCellClick(event);\'>'+value+'</a>';
    }
    return '';
}

function commonLinkCellClick(event){
    event = event || window.event;
    var t = event.target || event.srcElement;
    var $a = $(t);
    var grid = $a.parents('.datagrid-view').eq(0).children('table').eq(0);
    grid.datagrid('options').exOnClickLinkCell.call(grid[0], $a.parents('tr').attr('datagrid-row-index'), $a.attr('field'), $a.text());
    if (window.event) {
        event.cancelBubble=true;     // ie下阻止冒泡
    } else {
        event.stopPropagation();     // 其它浏览器下阻止冒泡
    }
}

