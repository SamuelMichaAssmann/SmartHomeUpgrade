var txtFile = new XMLHttpRequest();
    var allText = "file not found";
    txtFile.onreadystatechange = function () {
        if (txtFile.readyState === XMLHttpRequest.DONE && txtFile.status == 200) {
            allText = txtFile.responseText;
            //allText = allText.split("\n").join("<br>");
        }

        document.getElementById('test').innerHTML = allText;
    }
    txtFile.open("GET", '/android_asset/data.html', true);
    txtFile.send(null);