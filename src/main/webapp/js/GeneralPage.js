
$(document).ready(function(){
        jGeneral = {

        };
        $('#addBtn').on('click', function (e) {
            e.preventDefault();
            window.location.href='Adding.html';
         });
});

$(window).load(function(){
         jAut = {
            'regCallback' : function(data) {
               if(data.login == null){
                $('#userLogin').text("guest");
               }
               $('#userLogin').text(data.login);
            },
            'generalCallback' : function(data) {
                $('#generalTable > tbody:last').append(
                '<tr>'
                     + '<td><input maxlength="25" size="5"></td>'
                     + '<td><input maxlength="25" size="10"></td>'
                     + '<td><input maxlength="25" size="10"></td>'
                     + '<td><input maxlength="25" size="10"></td>'
                     + '<td><input maxlength="25" size="10"></td>'
                     + '<td><input maxlength="25" size="10"></td>'
                     + '<td><input maxlength="25" size="10"></td>'
                     + '<td>'
                         + '<input maxlength="25" size="10" id="bid" type="number">'
                         + '<button>Bid</button>'
                     + '</td>'
                + '</tr>');

            }
         };
         $.post("/authentication", {login: $('#login').val(), password: $('#password').val()}, jAut.regCallback, 'json')
         $.post("/general", jAut.generalCallback, 'json')
});