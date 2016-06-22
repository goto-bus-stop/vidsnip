var $ = require('jquery')

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
    return $('<li />').addClass('media').append(left, body)
}

module.exports = function (editor) {
    editor = $(editor)
    var query = editor.find('.video-search-query input')
    var results = editor.find('.video-search-results')
    var loader = editor.find('.video-search-loading')

    var request
    var requestTimer
    query.on('input', function () {
        loader.addClass('is-visible')
        clearTimeout(requestTimer)
        if (request) {
            request.abort()
            request = null
        }
        requestTimer = setTimeout(function () {
            request = $.getJSON('/new/search', { q: query.val() })
            request.done(function (result) {
                request = null
                loader.removeClass('is-visible')
                results.empty().append(
                    result.map(resultTemplate)
                )
            })
        }, 100)
    })
}
