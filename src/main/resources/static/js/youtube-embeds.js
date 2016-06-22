var $ = require('jquery')
var youtubePlayer = require('youtube-player')

module.exports = function (embeddable) {
    embeddable = $(embeddable)
    var videoId = embeddable.data('videoId')
    var start = embeddable.data('start')
    var end = embeddable.data('end')

    embeddable.one('click', function (e) {
        // Some embeds are placed inside links.
        e.preventDefault()

        youtubePlayer(embeddable[0], {
            height: '100%',
            width: '100%',
            videoId: videoId,
            playerVars: {
              autoplay: 1,
              controls: 0,        // no controls
              rel: 0,             // do not show related videos after the video finishes
              showinfo: 0,        // do not show video title etc in the frame
              iv_load_policy: 3,  // disable annotations
              modestbranding: 1,  // hide youtube logo
              start: start,
              end: end
            }
        })
    })
}
