var $ = require('jquery')
var youtubePlayer = require('youtube-player')

function resultTemplate (video) {
    var thumbnail = $('<img />')
        .addClass('media-object')
        .attr({ alt: 'Thumbnail', src: video.thumbnail })
    var title = $('<h4 />').addClass('media-heading').text(video.title)
    var description = $('<p />').text(video.description)
    var left = $('<div />').addClass('media-left')
        .append(thumbnail)
    var body = $('<div />').addClass('media-body').append(
        $('<div />').css({ 'max-height': '100px', overflow: 'hidden' })
            .append(title, description)
    )
    return $('<li />')
        .addClass('media video-search-result')
        .data('video', video)
        .append(left, body)
}

module.exports = function (editor) {
    editor = $(editor)

    var $query = editor.find('.video-search-query input')
    var $results = editor.find('.video-search-results')
    var $loader = editor.find('.video-search-loading')
    var $video = editor.find('[data-hook="video-editor-video"]')

    var player = youtubePlayer($video.children()[0], {
        playerVars: {
            controls: 0,       // no controls
            rel: 0,            // do not show related videos after the video finishes
            showinfo: 0,       // do not show video title etc in the frame
            iv_load_policy: 3, // disable annotations
            modestbranding: 1  // hide youtube logo
        }
    })

    var request
    var requestTimer
    $query.on('input', function () {
        $loader.addClass('is-visible')
        clearTimeout(requestTimer)
        if (request) {
            request.abort()
            request = null
        }
        requestTimer = setTimeout(function () {
            request = $.getJSON('/new/search', { q: $query.val() })
            request.done(function (result) {
                $request = null
                $loader.removeClass('is-visible')
                $results.empty().append(
                    result.map(resultTemplate)
                )
            })
        }, 100)
    })

    $results.on('click', '.media', function (e) {
        var item = $(e.currentTarget)
        var video = item.data('video')
        selectVideo(video)
    })

    function selectVideo (video) {
        $results.children().each(function () {
            var v = $(this).data('video')
            if (v === video) {
                $(this).addClass('is-selected')
            } else {
                $(this).removeClass('is-selected')
            }
        })

        if (video) {
            player.loadVideoById(video.id)
        } else {
            player.stopVideo();
        }
    }
}
