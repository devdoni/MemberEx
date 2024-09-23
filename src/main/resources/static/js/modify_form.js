
    let profileDtos = [];
    let currentImageIndex = 0;
    let m_id = '';

    $('document').ready(function () {
        console.log('DOCUMENT IS READY');
        init_events();
    })

    const init_events = () => {
        hideModal();
        $('.profile_thumbnail').click(function () {
            ajax_profileLoad();
            showModal();
        });

        $('.close').click(function () {
            hideModal();
        });
    }

    const hideModal = () => {
        console.log('hideModal()');
        $('.modal').hide();

    }
    const showModal = () => {
        console.log('showModal()');
        $('.modal').show();

        if (profileDtos.length > 0) {
            let src = $('.profile_thumbnail').attr('src');
            $('.modal-image').attr('src', src);
        }
    }
    const showPreviousImage = () => {
        if (profileDtos.length > 0) {
            currentImageIndex = (currentImageIndex === profileDtos.length - 1) ? 0 : currentImageIndex + 1;
            let src = '/profileImg/' + m_id + '/' + profileDtos[currentImageIndex];
            $('.modal-image').attr('src', src);
        }
    }

    const showNextImage = () => {
        if (profileDtos.length > 0) {
            currentImageIndex = (currentImageIndex === 0) ? profileDtos.length - 1 : currentImageIndex - 1;
            let src = '/profileImg/' + m_id + '/' + profileDtos[currentImageIndex];
            $('.modal-image').attr('src', src);
        }
    }


    const ajax_profileLoad = () => {
        console.log('ajax_profileLoad()');

        $.ajax({
            url: '/member/getProfile',
            method: 'POST',
            success : (data) => {
                console.log('AJAX COMMUNICATION SUCCESS');
                m_id = data.profileImgDtos[0].m_id;
                profileDtos = data.profileImgDtos.map(dto => dto.pi_name);
                console.log('data =>', profileDtos);

                currentImageIndex = 0;
                showModal();
            },
            error : () => {
                console.log('AJAX COMMUNICATION ERROR');
            }
        });

    }

const modifyConfirm = () => {
    console.log('modifyConfirm()');

    let form = document.modify_form;
    if (form.m_mail.value === '') {
        alert('INPUT USER MAIL');
        form.m_mail.focus();

    } else if (form.m_phone.value === '') {
        alert('INPUT USER PHONE');
        form.m_phone.focus();

    } else if (form.m_gender.value === '') {
        alert('INPUT USER GENDER');
        form.m_gender.focus();

    } else if (form.querySelectorAll('input[name="hobbys"]:checked').length < 3) {
        alert('CHECK HOBBY');

    } else {
        form.submit();
    }
}

const deleteConfirm = () => {
    console.log('deleteConfirm()');

    if (confirm('정말로 탈퇴하시겠습니까?')) {
        location.href = `/member/delete_confirm`
    }
}