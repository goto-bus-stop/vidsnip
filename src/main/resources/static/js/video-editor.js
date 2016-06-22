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

function padZero (n) {
    return n < 10 ? `0${n}` : n
}

function formatDuration (duration) {
    var h = Math.floor(duration / 3600)
    var m = Math.floor((duration % 3600) / 60)
    var s = padZero(Math.floor(duration % 60))
    return (h > 0 ? [ h, padZero(m), s ] : [ m, s ]).join(':')
}

function parseDuration (duration) {
    var parts = duration.split(':').reverse()
    var mult = [ 1, 60, 60, 24 ]
    return parts.map(function (part) {
        return parseInt(part, 10)
    }).map(function (part, i) {
        return part * mult[i]
    }).reduce(function (a, b) {
        return a + b
    })
}

module.exports = function (editor) {
    editor = $(editor)

    var $query = editor.find('.video-search-query input')
    var $results = editor.find('.video-search-results')
    var $loader = editor.find('.video-search-loading')
    var $video = editor.find('[data-hook="video-editor-video"]')
    var $timeline = editor.find('.snip-video-timeline')
    var $knob = $timeline.find('.snip-video-timeline-snippet')
    var $startTime = editor.find('[data-hook="video-editor-start"]')
    var $endTime = editor.find('[data-hook="video-editor-end"]')
    var $videoId = editor.find('[data-hook="video-editor-video-id"]')

    var player = youtubePlayer($video.children()[0], {
        playerVars: {
            controls: 0,       // no controls
            rel: 0,            // do not show related videos after the video finishes
            showinfo: 0,       // do not show video title etc in the frame
            iv_load_policy: 3, // disable annotations
            modestbranding: 1  // hide youtube logo
        }
    })

    player.on('ready', updateTimeline)
    var timelineInterval
    player.on('stateChange', function (e) {
        if (e.data === 1) {
            timelineInterval = setInterval(updateTimeline, 1000)
        } else {
            clearInterval(timelineInterval)
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

        $videoId.val(video.id || '')

        if (video) {
            player.loadVideoById(video.id)
        } else {
            player.stopVideo();
        }
    }

    var seekTimer
    $startTime.on('input', function () {
        clearTimeout(seekTimer)
        var startTime = parseDuration($startTime.val())
        player.seekTo(startTime, false)
        seekTimer = setTimeout(function () {
            player.seekTo(startTime, true)
        }, 300)
    })

    function updateTimeline () {
        Promise.all([ player.getDuration(), player.getCurrentTime() ])
            .then(function (arr) {
                var duration = arr[0]
                var currentTime = arr[1]
                console.log(arr)
                var snipDuration = 10
                $knob.css({ 'margin-left': (currentTime / duration * 100) + '%' })
                if (!$startTime.is(':focus')) {
                    $startTime.val(formatDuration(currentTime))
                    $endTime.val(formatDuration(currentTime + snipDuration))
                }
            })
    }
}
