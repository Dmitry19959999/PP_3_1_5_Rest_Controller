$(async function () {
    await showAuthUser();
});

async function showAuthUser() {

    fetch("/user/api/user", {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Referer': null
        }
    })
        .then((response) => response.json())
        .then(data => {
            $('#authUser').append(data.email);
            let roles = data.roles.map(role => " " + role.roleName.substring(5));
            $('#authUserRoles').append(roles);
            let user = `$(
                <tr> 
                    <td>${data.id}</td>
                    <td>${data.name}</td>
                    <td>${data.surname}</td>
                    <td>${data.age}</td>
                    <td>${data.email}</td>
                    <td>${roles}</td>
                </tr>    
                )`;
            $('#authUserPanel').append(user);
        })
}