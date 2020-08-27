

    function updateRole(userId) {
        // 获取<meta>标签中封装的_csrf信息
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");
        const role = document.querySelector('input[name = "role"]:checked').value;
        if(confirm('确定修改该用户权限吗?')){
            $.ajax({
                type:'post',
                url : '/admin/config/updateRole',
                data: {id:userId,role:role},
                dataType: 'json',
                beforeSend : function(xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (result) {
                    if (result && result.success) {
                        window.alert("用户权限成功");
                        window.location.reload();
                    } else {
                        window.alert(result.msg || '用户权限修改失败')
                    }
                }
            });
        }
    }




