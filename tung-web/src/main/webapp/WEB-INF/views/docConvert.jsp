<%@page contentType="text/html;charset=utf-8" language="java" %>
<html>
<header></header>
<script src="/assets/jquery.min.js"></script>
<body>
<form action="/poi/convert" method="POST" enctype="multipart/form-data">
    <input type="file" name="file" id="upload"/>
    <input type="submit" />
</form>
<div id="result" contenteditable="true"></div>
<script>
$(function(){
    $('form').on('submit', function () {
        var formData = new FormData();
        formData.append("file", $('#upload')[0].files[0]);
        formData.append("name", 'file');
        $.ajax({
            url : $(this).attr('action'),
            type : 'POST',
            data : formData,
            // 不要去处理发送的数据
            processData : false,
            // 不要去设置Content-Type请求头
            contentType : false,
            beforeSend:function(){
                console.log("正在进行，请稍候");
            },
            success : function(d) {
               console.log(d)
                $('#result').html(d);
            },
            error : function(e) {
                console.log("error");
                console.log(e);
                $('#result').html(e.responseText);
            }
        });
        return false;
    });
});
</script>
</body>
</html>