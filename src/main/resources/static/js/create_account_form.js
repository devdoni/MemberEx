const createAccountForm = () => {
    console.log('createAccountForm()');

    let form = document.create_account_form;
    if (form.m_id.value === '') {
        alert('INPUT USER ID');
        form.m_id.focus();

    } else if (form.m_pw.value === '') {
        alert('INPUT USER PW');
        form.m_pw.focus();

    } else if (form.m_pw_again.value === '') {
        alert('INPUT USER PW AGAIN');
        form.m_pw_again.focus();

    } else if (form.m_pw.value !== form.m_pw_again.value) {
        alert('CHECK PW');
        form.m_pw.value = '';
        form.m_pw_again.value = '';
        form.m_pw.focus();

    } else if (form.m_mail.value === '') {
        alert('INPUT USER MAIL');
        form.m_mail.focus();

    } else if (form.m_phone.value === '') {
        alert('INPUT USER PHONE');
        form.m_phone.focus();

    } else if (form.m_gender.value === '') {
        alert('INPUT USER GENDER');
        form.m_gender.focus();

    } else if (form.m_gender.value === '') {
        alert('INPUT USER GENDER');
        form.m_gender.focus();

    } else if (form.querySelectorAll('input[name="hobbys"]:checked').length < 3) {
        alert('CHECK HOBBY');

    } else {
        form.submit();
    }
}