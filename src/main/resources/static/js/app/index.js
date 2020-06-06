/*
     [왜 init을 그냥 사용하지 않고 main으로 묶어서 사용하는가?]
     브라우저의 스코프(scope)는 공용 공간으로 쓰이기 때문에 나중에 로딩된 js의 init, save가 먼저 로딩된 js의 function을 덮어쓰게 된다.
          여러 사람이 참여하는 프로젝트에서는 중복된 함수이름은 자주 발생할 수 있다. 모든 function의 이름을 확인하며 만들 수 없기에 유효범위(scope)를 만들어 이용한다.
*/
var main={
    init(){
        var _this = this;
        $('#btn-save').on('click', ()=>{
            _this.save();
        });
        $('#btn-update').on('click', ()=>{
           _this.update();
        });
        $('#btn-delete').on('click', ()=>{
            _this.delete();
        })
    },
    save : function(){
        var data = {
            title : $('#title').val(),
            author : $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('글이 등록되었습니다');
            // 글 등록이 성공하면 해당하는 위치로 이동한다.
            window.location.href = '/';
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },
    update : function(){
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(()=>{
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(()=>{
            alert(JSON.stringify(error));
        });
    },
    delete : function(){
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(()=>{
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail((error)=>{
            alert(JSON.stringify(error));
        });
    }
};

main.init();