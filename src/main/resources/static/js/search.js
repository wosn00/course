$(function () {
    $("#sub").click(function () {
        let type = $("input[name='type']:checked").val();
        let searchText = $("input[name='searchText']").val();
        let url="/fuzzySearch?type="+type+"&searchText="+searchText;
        window.location.href=url;
    });

})
