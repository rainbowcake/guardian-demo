package co.zsmb.rainbowcake.guardiandemo.ui

import android.os.Bundle
import co.zsmb.rainbowcake.guardiandemo.ui.list.ListFragment
import co.zsmb.rainbowcake.navigation.SimpleNavActivity

class MainActivity : SimpleNavActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            navigator.add(ListFragment())
        }
    }

}
