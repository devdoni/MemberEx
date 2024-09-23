const loginConfirm = () => {
    console.log('createAccountForm()');

    let form = document.login_form;
    if (form.m_id.value === '') {
        alert('INPUT USER ID');
        form.m_id.focus();

    } else if (form.m_pw.value === '') {
        alert('INPUT USER PW');
        form.m_pw.focus();

    } else {
        form.submit();
    }
}