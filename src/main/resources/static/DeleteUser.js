$(async function (id) {
    await deleteUser(id);
});

async function deleteUser(id) {

    await showUserById(id).then(user => {
        $('#deleteUserId').val(user.id)
        $('#deleteUserName').val(user.name)
        $('#deleteUserSurname').val(user.surname)
        $('#deleteUserAge').val(user.age)
        $('#deleteUserEmail').val(user.email)
        user.roles.forEach(role => {
            let option = new Option(role.roleName.substring(5), role.id);
            $('#deleteUserRoles').append(option);
        })
    })

    const form = document.forms["deleteUserForm"];
    form.addEventListener('submit', delProcess)

    async function delProcess(event) {
        event.preventDefault();
        fetch("/admin/api/admin/" + id, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Referer': null
            }
        }).then(() => {
                $('#delFormCloseButton').click();
                showUsersTable();
            }
        )
    }
}