$(async function (id) {
    await editUser(id);
});

async function editUser(id) {

    const form = document.forms["editUserForm"];
    $('#editUserRoles').empty();

    await getAllRoles().then(roles => {
        roles.forEach(role => {
            let option = new Option(role.roleName.substring(5), role.id);
            option.setAttribute("id", role.roleName);
            $('#editUserRoles').append(option);
        })
    })

    await showUserById(id).then(user => {
        $('#editUserId').val(user.id)
        $('#editUserName').val(user.name)
        $('#editUserSurname').val(user.surname)
        $('#editUserAge').val(user.age)
        $('#editUserPassword').val(user.password)
        $('#editUserEmail').val(user.email)

        user.roles.forEach(role => {
            console.log(role.roleName.substring(5));
            document.getElementById(role.roleName).selected = true;
        })
    })

    form.addEventListener('submit', editProcess);

    async function editProcess(event) {
        event.preventDefault();

        let editedUser = await userData(form);

        fetch("/admin/api/admin/" + id, {
            method: 'PATCH',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Referer': null
            },
            body: editedUser
        }).then(() => {
            $('#editFormCloseButton').click();
            showUsersTable();
        })
    }
}