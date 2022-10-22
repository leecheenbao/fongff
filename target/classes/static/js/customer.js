function showInfo(index){
    console.log("init CKEDITOR content-ckeditor-" + index);
    //初始化ckeditor
    CKEDITOR.replace("content-ckeditor-"+index);
}



function deleteInfo(indexR,module){
    console.log("delete " + indexR);

    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success',
        cancelButton: 'btn btn-danger'
      },
      buttonsStyling: false
    })

    swalWithBootstrapButtons.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, delete it!',
      cancelButtonText: 'No, cancel!',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
      let header = $("meta[name='_csrf_header']").attr("content");
      let token = $("meta[name='_csrf']").attr("content");
          $.ajax({
                type: 'post',
                url: '/fongff/action/deleteContent',
                data: {
                  'indexR': indexR,
                  'module': module
                },
                beforeSend: function (xhr) {
                       xhr.setRequestHeader(header, token);
                },
                success: function(msg) {
                  window.location.reload();
                }
              });
        swalWithBootstrapButtons.fire(
          'Deleted!',
          'Your file has been deleted.',
          'success'
        )
      } else if (
        /* Read more about handling dismissals below */
        result.dismiss === Swal.DismissReason.cancel
      ) {
        swalWithBootstrapButtons.fire(
          'Cancelled',
          'Your imaginary file is safe :)',
          'error'
        )
      }
    })

}

$(function (){
    $(".img-form").on('change', '.upload', function() {
        var _this = this;
        $.filePreview.create(this, {
            isReader: false,
            progress: function (key, percent){
                console.log(percent)
            },
            success: function (key, obj){
                var $img = $(_this).parents(".img-form").find(".pre-img").eq(key);
                $img.attr('src',obj.preview);
            }
        });
    });
})



