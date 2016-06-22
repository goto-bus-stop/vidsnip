var $ = window.jQuery = require('jquery')
require('bootstrap')
require('bootstrap-material-design')

$.material.init()

var createVideoEditor = require('./video-editor')

$(function () {
    var editors = $('[data-hook="video-editor"]')
    if (editors.length > 0) {
        editors.toArray().forEach(createVideoEditor)
    }
})
