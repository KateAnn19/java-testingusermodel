package com.lambdaschool.usermodel.models;

import java.util.Objects;

public class UserRolesId
{
    private long user;
    private long role;

    public UserRolesId()
    {
    }

    public long getUser()
    {
        return user;
    }

    public void setUser(long user)
    {
        this.user = user;
    }

    public long getRole()
    {
        return role;
    }

    public void setRole(long role)
    {
        this.role = role;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        UserRolesId that = (UserRolesId) o;
        return user == that.user &&
            role == that.role;
    }

    @Override
    public int hashCode()
    {
        return 37;
    }
}
