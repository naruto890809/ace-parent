function initMap(){
    var map = new BMap.Map("baiduMap"); // 创建地图实例
    var point = null; // 创建点坐标
    if(!$("#lng").val()||!$("#lat").val()||$("#lng").val()==""||$("#lat").val()==""){
        point = new BMap.Point(117.294077,31.754632);
    }else{
        point = new BMap.Point($("#lng").val(),$("#lat").val());
    }

    map.centerAndZoom(point, 15);
    map.addControl(new BMap.NavigationControl());
    map.addControl(new BMap.ScaleControl());
    map.enableScrollWheelZoom();    //启用滚轮放大缩小
    map.enableContinuousZoom();
    var marker = new BMap.Marker(map.getCenter());  // 创建标注
    map.addOverlay(marker);
    marker.enableDragging();
    marker.addEventListener("dragend", function(e){
        $("#lng").val(e.point.lng);
        $("#lat").val(e.point.lat);

    });
    map.addEventListener("click", function(e){
        $("#lng").val(e.point.lng);
        $("#lat").val(e.point.lat);
        marker.setPosition(e.point);
    });
    var local = new BMap.LocalSearch(map, {
        renderOptions:{map: map},
        pageCapacity: 5,
        onSearchComplete: function(results){
            if (local.getStatus() == BMAP_STATUS_SUCCESS){
                $("#lng").val(results.getPoi(0).point.lng);
                $("#lat").val(results.getPoi(0).point.lat);
                marker.setPosition(results.getPoi(0).point);
            }else{
                alert("未找到该地点,请尝试加上省市名");
            }
        }
    });
    function myFun(result){
        var cityName = result.name;
        map.setCenter(cityName);
        marker.setPosition(result.center);
        $("#lng").val(result.center.lng);
        $("#lat").val(result.center.lat);

    }
    $("#search").click(function(){
        local.search($("#searchTxt").val());
    });
    var myCity = new BMap.LocalCity();
    if(!$("#lng").val()||!$("#lat").val()||$("#lng").val()==""||$("#lat").val()==""){
        myCity.get(myFun);
    }
}




