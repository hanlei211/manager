<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>hlc</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="../../../layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../../../style/admin.css" media="all">
</head>
<body>
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-card-header">
        <span class="layui-breadcrumb pull-right">
          <a href="#!home_console">首页</a>
          <a><cite>用户管理</cite></a>
        </span>
        </div>
        <div class="layui-card">
            <div class="layui-form layui-card-header layuiadmin-card-header-auto">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">搜索:</label>
                        <#--<div class="layui-input-inline">-->
                            <#--<select id="user-search-key">-->
                                <#--<option value="">-请选择-</option>-->
                                <#--<option value="user_id">ID</option>-->
                                <#--<option value="username">账号</option>-->
                                <#--<option value="nick_name">用户名</option>-->
                                <#--<option value="phone">手机号</option>-->
                            <#--</select>-->
                        <#--</div>-->
                        <div class="layui-input-inline">
                            <input id="user-search-value" class="layui-input search-input" type="text"
                                   placeholder="输入关键字"/>
                        </div>
                    </div>
                    <div class="layui-btn-group">
                        <button id="user-btn-search" class="layui-btn icon-btn" data-type="reload"><i
                                class="layui-icon">&#xe615;</i>搜索
                        </button>
                        <button id="user-btn-add" class="layui-btn icon-btn" lay-tips="新用户密码为123456"><i
                                class="layui-icon">&#xe654;</i>添加
                        </button>
                    </div>
                </div>
            </div>

            <table class="layui-table" id="user-table" lay-filter="user-table"></table>
        </div>
    </div>
</div>

<!-- 表格操作列 -->
<script type="text/html" id="user-table-bar">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">修改</a>
    <a class="layui-btn layui-btn-xs" lay-event="reset">重置密码</a>
</script>

<!-- 表格状态列 -->
<script type="text/html" id="user-tpl-state">
    <input type="checkbox" lay-filter="user-tpl-state" value="{{d.userId}}" lay-skin="switch" lay-text="正常|锁定"
           {{d.state==0?'checked':''}}/>
</script>

<script src="../../../layui/layui.js"></script>
<script src="../../../js/jquery-3.2.1.min.js"></script>


<script>
    layui.config({
        base: '../../../' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use('index');
</script>
<script>
    layui.use(['form', 'table', 'util', 'admin', 'element'], function () {
        var form = layui.form;
        var table = layui.table;
        var layer = layui.layer;
        var util = layui.util;
        var admin = layui.admin;
        var element = layui.element;

        form.render('select');

        // 渲染表格
        table.render({
            elem: '#user-table',
            url: '/user/list',
            page: true,
            cols: [[
                {type: 'numbers'},
                {field: 'username', sort: true, title: '账号'},
                {field: 'nick_name', sort: true, title: '用户名'},
                {field: 'phone', sort: true, title: '手机号'},
                {field: 'sex', sort: true, title: '性别'},
                {
                    sort: true, templet: function (d) {
                        return util.toDateString(d.create_time);
                    }, title: '创建时间'
                },
                {field: 'state', sort: true, templet: '#user-tpl-state', title: '状态'},
                {align: 'center', toolbar: '#user-table-bar', title: '操作'}
            ]]
        });

        // 添加按钮点击事件
        $('#user-btn-add').click(function () {
            showEditModel();
        });

        // 工具条点击事件
        table.on('tool(user-table)', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;

            if (layEvent === 'edit') { // 修改
                showEditModel(data);
            } else if (layEvent === 'reset') { // 重置密码
                layer.confirm('确定重置此用户的密码吗？', function (i) {
                    layer.close(i);
                    layer.load(2);
                    $.post('system/user/restPsw', {
                        userId: obj.data.userId
                    }, function (data) {
                        layer.closeAll('loading');
                        if (data.code == 200) {
                            layer.msg(data.msg, {icon: 1});
                        } else {
                            layer.msg(data.msg, {icon: 2});
                        }
                    });
                });
            }
        });

        // 显示表单弹窗
        var showEditModel = function (data) {
            var title = data ? '修改用户' : '添加用户';
            admin.putTempData('t_user', data);
            admin.popupCenter({
                title: title,
                path: 'system/user/editForm',
                finish: function () {
                    table.reload('user-table', {});
                }
            });
        };

        // 搜索按钮点击事件
        $('#user-btn-search').click(function () {
            var value = $('#user-search-value').val();
            table.reload('user-table', {where:
                        {username: value}});
        });

        // 修改user状态
        form.on('switch(user-tpl-state)', function (obj) {
            layer.load(2);
            $.post('system/user/updateState', {
                userId: obj.elem.value,
                state: obj.elem.checked ? 0 : 1
            }, function (data) {
                layer.closeAll('loading');
                if (data.code == 200) {
                    layer.msg(data.msg, {icon: 1});
                    //table.reload('table-user', {});
                } else {
                    layer.msg(data.msg, {icon: 2});
                    $(obj.elem).prop('checked', !obj.elem.checked);
                    form.render('checkbox');
                }
            });
        });
    });
</script>
</body>
</html>