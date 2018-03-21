var table = layui.table;

$(function(){



    table.render({
        elem: '#table',
        id: 'table',
        even: false,// 隔行背景
        skin: 'line',// 行边框风格
        url: SERVER_ROUTER.PATH_QUERY_PAGE_CASE,
        method: 'GET',
        page: true,
        limits: [10, 20, 50],
        limit: 10,
        loading: true,
        width: '100%',
        request: {
            pageName: 'currentPage',
            limitName: 'limit'
        },
        cols: [
            [
                {
                    checkbox: false,
                    sort: false
                },
                {
                    field: 'id',
                    title: 'ID',
                    align: 'center'
                },
                {
                    field: 'hosName',
                    title: '医院名',
                    width: 150,
                    align: 'center',
                    edit: false
                },
                {
                    field: 'address',
                    title: '地址',
                    width: 250,
                    align: 'center',
                    edit: false
                },
                {
                    field: 'dName',
                    title: '主治医生-姓名',
                    width: 350,
                    align: 'center',
                    edit: false
                },
                {
                    field: 'dPhone',
                    title: '主治医生-电话',
                    width: 150,
                    align: 'center',
                    edit: false
                },
                {
                    field: 'uName',
                    title: '病人-姓名',
                    width: 350,
                    align: 'center',
                    edit: false
                },
                {
                    field: 'uPhone',
                    title: '病人-电话',
                    width: 150,
                    align: 'center',
                    edit: false
                },
                {
                    field: 'sicknessName',
                    title: '确诊病症',
                    width: 100,
                    align: 'center',
                    edit: false
                },
                {
                    field: 'content',
                    title: '诊断结果',
                    width: 350,
                    align: 'center',
                    edit: false
                },
                {
                    field: 'recipel',
                    title: '处方',
                    width: 350,
                    align: 'center',
                    edit: false
                },
                {
                    field: 'createTime',
                    title: '诊断时间',
                    width: 150,
                    align: 'center',
                    edit: false
                },
                {
                    title: 'OPT',
                    fixed: 'right',
                    align: 'center',
                    width: 200,
                    toolbar: '#toolBar'
                }
                // ,
                // {
                //     field: 'lastLoginTime',
                //     title: '最后登陆时间',
                //     templet: '<div>{{d.lastLoginTime == null ? "暂未登陆过" : d.lastLoginTime}}</div>',
                //     width: 200,
                //     align: 'center'
                // }
            ]
        ]
    });

    //监听工具条
    table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if(layEvent === 'del'){ //删除
            layer.confirm('真的删除该医院么', {offset: 't'}, function(index){

                //向服务端发送删除指令
                del(obj, index);
            });
        } else if(layEvent === 'edit'){ //编辑
            //do something


            update(obj);

        }
    });

});



var save = function(){

    var data = {};

    data.uid = '';
    data.did = '';
    data.hosId = '';
    data.content = '';


    $.ajax({
       url: SERVER_ROUTER.PATH_CASE_SAVE,
       type: 'POST',
       dataType: 'JSON',
       data: data,
       success: function(data){
           if(data && data.status == SERVER_RESPONSE_CODE.OK){
               if(data.data){
                   table.reload('table');
               }
           }
           layer.msg(data.msg, {
               offset: 't'
           });
       }
    });

    return false;
}


var del = function(obj, index){
    var data = obj.data;
    $.ajax({
       url: SERVER_ROUTER.PATH_CASE_DELETE + '/' + data.id,
       type: 'DELETE',
       dataType: 'JSON',
       success: function(data){
           if(data && data.status == SERVER_RESPONSE_CODE.OK){
               obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
               layer.close(index);
           }
           layer.msg(data.msg, {
               offset: 't'
           });
       }
    });
}