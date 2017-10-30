$(document).ready(function() {
    var entries = [];

    $('#image-holder').click(function() {
        selectEntry();
    });

    var selectEntry = function() {
        var numEntries = entries.length;
        if (numEntries == 0) {
            return;
        }
        var num = Math.floor(Math.random() * numEntries);
        updateImage(num);
    };

    var updateImage = function(num) {
        var entry = entries[num];
        var title = entry.title;
        var link = entry.link;
        var image = entry.imageUrl;

        $('#image-holder').html("<img src=\"" + image + "\"'/>");
        $('#caption-holder').html("<p><a href='" + link + "'>" + title + "</a></p>");
    };

    $.ajax({
        url: "data/octocat-index.json",
        dataType: "json"
    })
        .done(function(data) {
            entries = data.entries;
            selectEntry();
        })
        .fail(function(jqXHR, textStatus, errorThrown) {
            if (console) {
                console.log(textStatus);
                console.log(errorThrown);
            }
        });
});