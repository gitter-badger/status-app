var gulp = require('gulp')
var bower = require('gulp-bower')

gulp.task('bower', function() {
    bower()
        .pipe(gulp.dest('public/'))
})

gulp.task('default', ['bower'])
