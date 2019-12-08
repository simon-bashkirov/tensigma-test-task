package com.testtask.ui.mapper

import com.testtask.domain.mapper.Mapper
import com.testtask.domain.model.user.Profile
import com.testtask.ui.model.ProfileShort

class ProfileMapper(val email: String) : Mapper<Profile, ProfileShort> {

    override fun mapTo(source: Profile) =
        source.run {
            ProfileShort(
                firstName = firstName,
                lastName = lastName,
                email = email
            )
        }
}