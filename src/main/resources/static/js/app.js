var $ = window.jQuery = require('jquery')
require('bootstrap')
require('bootstrap-material-design')

$.material.init()

var createVideoEditor = require('./video-editor')
var createYouTubeEmbeddable = require('./youtube-embeds')

$(function () {
    var editors = $('[data-hook="video-editor"]')
    if (editors.length > 0) {
        editors.toArray().forEach(createVideoEditor)
    }

    var embeddables = $('[data-hook="embed"]')
    if (embeddables.length > 0) {
        embeddables.toArray().forEach(createYouTubeEmbeddable)
    }
})
