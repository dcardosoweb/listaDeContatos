package com.picpay.desafio.android.presentation.activity

import com.picpay.desafio.android.core.BaseActivity
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.utils.viewBinding

class MainActivity : BaseActivity() {
    override val binding by viewBinding(ActivityMainBinding::inflate)
}
