package info.imdang.app.ui.join

import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.app.base.BaseViewModel
import info.imdang.app.model.auth.GenderType
import info.imdang.app.util.validateBirthDate
import info.imdang.app.util.validateNickname
import info.imdang.component.model.SelectionVo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class JoinViewModel @Inject constructor() : BaseViewModel() {

    private val _isAgreeTerm = MutableStateFlow(false)
    val isAgreeTerm = _isAgreeTerm.asStateFlow()

    private val _isAgreePrivacy = MutableStateFlow(false)
    val isAgreePrivacy = _isAgreePrivacy.asStateFlow()

    private val _isAgreeMarketing = MutableStateFlow(false)
    val isAgreeMarketing = _isAgreeMarketing.asStateFlow()

    val isAgreeAndContinueButtonEnabled = combine(
        isAgreeTerm,
        isAgreePrivacy
    ) { isAgreeTerm, isAgreePrivacy ->
        isAgreeTerm && isAgreePrivacy
    }.toStateFlow(false)

    private val _nickname = MutableStateFlow("")
    val nickname = _nickname.asStateFlow()

    private val _nicknameErrorMessage = MutableStateFlow("")
    val nicknameErrorMessage = _nicknameErrorMessage.asStateFlow()

    val isNicknameValid = combine(
        nickname.isNotBlank(),
        nicknameErrorMessage.isBlank()
    ) { isNicknameNotBlank, isNicknameErrorMessageBlank ->
        isNicknameNotBlank && isNicknameErrorMessageBlank
    }.toStateFlow(false)

    private val _birthDate = MutableStateFlow("")
    val birthDate = _birthDate.asStateFlow()

    private val _birthDateErrorMessage = MutableStateFlow("")
    val birthDateErrorMessage = _birthDateErrorMessage.asStateFlow()

    val isBirthDateValid = combine(
        birthDate.isNotBlank(),
        birthDateErrorMessage.isBlank()
    ) { isBirthDateNotBlank, isBirthDateErrorMessageBlank ->
        isBirthDateNotBlank && isBirthDateErrorMessageBlank
    }.toStateFlow(false)

    private val _genders = MutableStateFlow(
        GenderType.entries.map { SelectionVo(it.gender) }
    )
    val genders = _genders.asStateFlow()

    private val isGenderValid = genders
        .map { it.any { it.isSelected } }
        .toStateFlow(false)

    val isButtonEnabled = combine(
        isNicknameValid,
        isBirthDateValid,
        isGenderValid
    ) { isNicknameValid, isBirthDateValid, isGenderValid ->
        isNicknameValid && isBirthDateValid && isGenderValid
    }.toStateFlow(false)

    fun updateAllAgree() {
        if (isAgreeTerm.value && isAgreePrivacy.value && isAgreeMarketing.value) {
            _isAgreeTerm.value = false
            _isAgreePrivacy.value = false
            _isAgreeMarketing.value = false
        } else {
            _isAgreeTerm.value = true
            _isAgreePrivacy.value = true
            _isAgreeMarketing.value = true
        }
    }

    fun updateAgreeTerm() {
        _isAgreeTerm.value = !isAgreeTerm.value
    }

    fun updateAgreePrivacy() {
        _isAgreePrivacy.value = !isAgreePrivacy.value
    }

    fun updateAgreeMarketing() {
        _isAgreeMarketing.value = !isAgreeMarketing.value
    }

    fun updateNickname(nickname: String) {
        _nickname.value = nickname
        validateNickname()
    }

    fun updateBirthDate(birthDate: String) {
        _birthDate.value = reformatBirthDate(birthDate)
        validateBirthDate()
    }

    fun updateGender(selection: SelectionVo) {
        _genders.value = genders.value.map {
            it.copy(isSelected = selection.name == it.name)
        }
    }

    private fun reformatBirthDate(birthDate: String): String {
        var newBirthDate = birthDate.replace(".", "")
        if (newBirthDate.length > 4) {
            newBirthDate = newBirthDate.substring(0, 4) + "." + newBirthDate.substring(4)
        }
        if (newBirthDate.length > 7) {
            newBirthDate = newBirthDate.substring(0, 7) + "." + newBirthDate.substring(7)
        }
        return newBirthDate
    }

    private fun validateNickname() {
        _nicknameErrorMessage.value = nickname.value.validateNickname()
    }

    private fun validateBirthDate() {
        _birthDateErrorMessage.value = birthDate.value.validateBirthDate()
    }
}
