;(function () {

function $ (sel, map) {
    return [].map.call(document.querySelectorAll(sel), map || Object)
}

window.onYouTubeIframeAPIReady = function () {
    $('[data-hook*="embed"]', function (embed) {
        var videoId = embed.dataset.videoId
        var start = embed.dataset.start
        var end = embed.dataset.end
        new YT.Player(embed, {
            height: '100%',
            width: '100%',
            videoId: videoId,
            playerVars: {
              controls: 0,
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

var script = document.createElement('script')
script.src = 'https://www.youtube.com/iframe_api'
script.async = true
document.body.appendChild(script)

}())
