
$(document).ready(function(){
        jGeneral = {
        };
        $('#addBtn').on('click', function (e) {
            e.preventDefault();
            window.location.href='/add';
         });
         $('#myProd').on('click', function (e) {
            e.preventDefault();
            window.location.href='/myProducts';
         });
         $('#backBtn').on('click', function (e) {
            e.preventDefault();
            window.location.href='/general';
         });
});
