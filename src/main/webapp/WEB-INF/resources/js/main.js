$(document).ready(function () {
    $('#CallboardTableContainer').jtable({
        title: 'Callboard',
        actions: {
            listAction: 'service/callboard/get',
            createAction: 'service/callboard/create',
            updateAction: 'service/callboard/update',
            deleteAction: 'service/callboard/remove'
        },
        fields: {
            postId: {
                key: true,
                create: false,
                edit: false,
                list: false
            },
            topic: {
                title: 'Topic',
                width: '40%'
            },
            message: {
                title: 'Message',
                width: '20%'
            }
        }
    });
    $('#CallboardTableContainer').jtable('load');
});
