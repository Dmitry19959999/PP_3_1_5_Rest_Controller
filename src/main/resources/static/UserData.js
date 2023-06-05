async function userData(form) {
    let user = new FormData(form);
    let userRoles = [];
    for (let i = 0; i < form.roles.options.length; i++) {
        if (form.roles.options[i].selected) userRoles.push({
            id: form.roles.options[i].value,
            name: form.roles.options[i].name
        })
    }
    user.set("roles", JSON.stringify(userRoles));
    return JSON.stringify(Object.fromEntries(user))
        .replaceAll("\\", "")
        .replaceAll("\"[{", "[{")
        .replaceAll("}]\"", "}]");
}