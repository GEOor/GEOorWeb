// Drawing Manager로 도형을 그릴 지도 div
var drawingMapContainer = document.getElementById('drawingMap'),
    drawingMap = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
var drawingMap = new kakao.maps.Map(drawingMapContainer, drawingMap);

var options = { // Drawing Manager를 생성할 때 사용할 옵션입니다
    map: drawingMap, // Drawing Manager로 그리기 요소를 그릴 map 객체입니다
    drawingMode: [ // Drawing Manager로 제공할 그리기 요소 모드입니다
        kakao.maps.drawing.OverlayType.MARKER,
        kakao.maps.drawing.OverlayType.POLYLINE,
        kakao.maps.drawing.OverlayType.RECTANGLE,
        kakao.maps.drawing.OverlayType.CIRCLE,
        kakao.maps.drawing.OverlayType.POLYGON
    ],
    // 사용자에게 제공할 그리기 가이드 툴팁입니다
    // 사용자에게 도형을 그릴때, 드래그할때, 수정할때 가이드 툴팁을 표시하도록 설정합니다
    guideTooltip: ['draw', 'drag', 'edit'],
    markerOptions: { // 마커 옵션입니다
        draggable: true, // 마커를 그리고 나서 드래그 가능하게 합니다
        removable: true // 마커를 삭제 할 수 있도록 x 버튼이 표시됩니다
    },
    polylineOptions: { // 선 옵션입니다
        draggable: true, // 그린 후 드래그가 가능하도록 설정합니다
        removable: true, // 그린 후 삭제 할 수 있도록 x 버튼이 표시됩니다
        editable: true, // 그린 후 수정할 수 있도록 설정합니다
        strokeColor: '#39f', // 선 색
        hintStrokeStyle: 'dash', // 그리중 마우스를 따라다니는 보조선의 선 스타일
        hintStrokeOpacity: 0.5  // 그리중 마우스를 따라다니는 보조선의 투명도
    },
    rectangleOptions: {
        draggable: true,
        removable: true,
        editable: true,
        strokeColor: '#39f', // 외곽선 색
        fillColor: '#39f', // 채우기 색
        fillOpacity: 0.5 // 채우기색 투명도
    },
    circleOptions: {
        draggable: true,
        removable: true,
        editable: true,
        strokeColor: '#39f',
        fillColor: '#39f',
        fillOpacity: 0.5
    },
    polygonOptions: {
        draggable: true,
        removable: true,
        editable: true,
        strokeColor: '#39f',
        fillColor: '#39f',
        fillOpacity: 0.5,
        hintStrokeStyle: 'dash',
        hintStrokeOpacity: 0.5
    }
};

// 위에 작성한 옵션으로 Drawing Manager를 생성합니다
var manager = new kakao.maps.drawing.DrawingManager(options);

// undo, redo 버튼의 disabled 속성을 설정하기 위해 엘리먼트를 변수에 설정합니다
var undoBtn = document.getElementById('undo');
var redoBtn = document.getElementById('redo');

// Drawing Manager 객체에 state_changed 이벤트를 등록합니다
// state_changed 이벤트는 그리기 요소의 생성/수정/이동/삭제 동작
// 또는 Drawing Manager의 undo, redo 메소드가 실행됐을 때 발생합니다
manager.addListener('state_changed', function() {

    // 되돌릴 수 있다면 undo 버튼을 활성화 시킵니다
    if (manager.undoable()) {
        undoBtn.disabled = false;
        undoBtn.className = "";
    } else { // 아니면 undo 버튼을 비활성화 시킵니다
        undoBtn.disabled = true;
        undoBtn.className = "disabled";
    }

    // 취소할 수 있다면 redo 버튼을 활성화 시킵니다
    if (manager.redoable()) {
        redoBtn.disabled = false;
        redoBtn.className = "";
    } else { // 아니면 redo 버튼을 비활성화 시킵니다
        redoBtn.disabled = true;
        redoBtn.className = "disabled";
    }

});

// 버튼 클릭 시 호출되는 핸들러 입니다
function selectOverlay(type) {
    // 그리기 중이면 그리기를 취소합니다
    manager.cancel();

    // 클릭한 그리기 요소 타입을 선택합니다
    manager.select(kakao.maps.drawing.OverlayType[type]);
}

// undo 버튼 클릭시 호출되는 함수입니다.
function undo() {
    // 그리기 요소를 이전 상태로 되돌립니다
    manager.undo();
}

// redo 버튼 클릭시 호출되는 함수입니다.
function redo() {
    // 이전 상태로 되돌린 상태를 취소합니다
    manager.redo();
}


/**
 * 도형 Indexing
 */
// 가져오기 버튼을 클릭하면 호출되는 핸들러 함수입니다
// Drawing Manager로 그려진 객체 데이터를 가져와 아래 지도에 표시합니다
function getDataFromDrawingMap() {
    // Drawing Manager에서 그려진 데이터 정보를 가져옵니다
    var data = manager.getData();

    // 아래 지도에 그려진 도형이 있다면 모두 지웁니다
    removeOverlays();

    // 지도에 가져온 데이터로 도형들을 그립니다
    drawMarker(data[kakao.maps.drawing.OverlayType.MARKER]);
    drawPolyline(data[kakao.maps.drawing.OverlayType.POLYLINE]);
    drawRectangle(data[kakao.maps.drawing.OverlayType.RECTANGLE]);
    drawCircle(data[kakao.maps.drawing.OverlayType.CIRCLE]);
    drawPolygon(data[kakao.maps.drawing.OverlayType.POLYGON]);
}

// 아래 지도에 그려진 도형이 있다면 모두 지웁니다
function removeOverlays() {
    var len = overlays.length, i = 0;

    for (; i < len; i++) {
        overlays[i].setMap(null);
    }

    overlays = [];
}

// Drawing Manager에서 가져온 데이터 중 마커를 아래 지도에 표시하는 함수입니다
function drawMarker(markers) {
    var len = markers.length, i = 0;

    for (; i < len; i++) {
        var marker = new kakao.maps.Marker({
            map: map,
            position: new kakao.maps.LatLng(markers[i].y, markers[i].x),
            zIndex: markers[i].zIndex
        });

        overlays.push(marker);
    }
}

// Drawing Manager에서 가져온 데이터 중 선을 아래 지도에 표시하는 함수입니다
function drawPolyline(lines) {
    var len = lines.length, i = 0;

    for (; i < len; i++) {
        var path = pointsToPath(lines[i].points);
        var style = lines[i].options;
        var polyline = new kakao.maps.Polyline({
            map: map,
            path: path,
            strokeColor: style.strokeColor,
            strokeOpacity: style.strokeOpacity,
            strokeStyle: style.strokeStyle,
            strokeWeight: style.strokeWeight
        });

        overlays.push(polyline);
    }
}

// Drawing Manager에서 가져온 데이터 중 사각형을 아래 지도에 표시하는 함수입니다
function drawRectangle(rects) {
    var len = rects.length, i = 0;

    for (; i < len; i++) {
        var style = rects[i].options;
        var rect = new kakao.maps.Rectangle({
            map: map,
            bounds: new kakao.maps.LatLngBounds(
                new kakao.maps.LatLng(rects[i].sPoint.y, rects[i].sPoint.x),
                new kakao.maps.LatLng(rects[i].ePoint.y, rects[i].ePoint.x)
            ),
            strokeColor: style.strokeColor,
            strokeOpacity: style.strokeOpacity,
            strokeStyle: style.strokeStyle,
            strokeWeight: style.strokeWeight,
            fillColor: style.fillColor,
            fillOpacity: style.fillOpacity
        });

        overlays.push(rect);
    }
}

// Drawing Manager에서 가져온 데이터 중 원을 아래 지도에 표시하는 함수입니다
function drawCircle(circles) {
    var len = circles.length, i = 0;

    for (; i < len; i++) {
        var style = circles[i].options;
        var circle = new kakao.maps.Circle({
            map: map,
            center: new kakao.maps.LatLng(circles[i].center.y, circles[i].center.x),
            radius: circles[i].radius,
            strokeColor: style.strokeColor,
            strokeOpacity: style.strokeOpacity,
            strokeStyle: style.strokeStyle,
            strokeWeight: style.strokeWeight,
            fillColor: style.fillColor,
            fillOpacity: style.fillOpacity
        });

        overlays.push(circle);
    }
}

// Drawing Manager에서 가져온 데이터 중 다각형을 아래 지도에 표시하는 함수입니다
function drawPolygon(polygons) {
    var len = polygons.length, i = 0;

    for (; i < len; i++) {
        var path = pointsToPath(polygons[i].points);
        var style = polygons[i].options;
        var polygon = new kakao.maps.Polygon({
            map: map,
            path: path,
            strokeColor: style.strokeColor,
            strokeOpacity: style.strokeOpacity,
            strokeStyle: style.strokeStyle,
            strokeWeight: style.strokeWeight,
            fillColor: style.fillColor,
            fillOpacity: style.fillOpacity
        });

        overlays.push(polygon);
    }
}

// Drawing Manager에서 가져온 데이터 중
// 선과 다각형의 꼭지점 정보를 kakao.maps.LatLng객체로 생성하고 배열로 반환하는 함수입니다
function pointsToPath(points) {
    var len = points.length,
        path = [],
        i = 0;

    for (; i < len; i++) {
        var latlng = new kakao.maps.LatLng(points[i].y, points[i].x);
        path.push(latlng);
    }

    return path;
}

/**
 * 마커 생성
 * @type {kakao.maps.Marker}
 */
// 지도를 클릭한 위치에 표출할 마커입니다
var marker = new kakao.maps.Marker({
    // 지도 중심좌표에 마커를 생성합니다
    position: drawingMap.getCenter()
});
// 지도에 마커를 표시합니다
marker.setMap(drawingMap);

// 지도에 클릭 이벤트를 등록합니다
// 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
kakao.maps.event.addListener(drawingMap, 'click', function(mouseEvent) {

    // 클릭한 위도, 경도 정보를 가져옵니다
    var latlng = mouseEvent.latLng;

    // 마커 위치를 클릭한 위치로 옮깁니다
    marker.setPosition(latlng);

    var message = '클릭한 위치의 위도는 ' + latlng.getLat() + ' 이고, ';
    message += '경도는 ' + latlng.getLng() + ' 입니다';

    var resultDiv = document.getElementById('clickLatlng');
    resultDiv.innerHTML = message;

});

