package com.example.interrapidismotest.ui.menu

import com.example.interrapidismotest.data.database.TableDao
import com.example.interrapidismotest.data.database.TableDatabase
import com.example.interrapidismotest.page.BaseUiTest
import com.example.interrapidismotest.page.Page.Companion.on
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test
import javax.inject.Inject
import com.example.interrapidismotest.R

@HiltAndroidTest
class MenuInstrumentationTest: BaseUiTest() {

    @Inject
    lateinit var database: TableDatabase

    @Inject
    lateinit var tableDao: TableDao

    @Test
    fun click_get_button_removes_red_text_and_shows_green_text() {
        on<MenuPage>()
            .wait(1)
            .on<MenuPage>()
            .verifyRedText("Nothing to show, please press 'Get Tables'")
            .clickButtonById(R.id.getTablesDataButton)
            .wait(1)
            .on<MenuPage>()
            .checkIfTextIsNotDisplayedById(R.id.tvEmpty)
            .verifyGreenText("To see the data, please press 'Show Tables'")
            .clickButtonById(R.id.showDataButton)
    }

    @Test
    fun click_get_button_and_later_show_button_shows_the_data() {
        on<MenuPage>()
            .wait(1)
            .on<MenuPage>()
            .verifyRedText("Nothing to show, please press 'Get Tables'")
            .clickButtonById(R.id.getTablesDataButton)
            .wait(1)
            .on<MenuPage>()
            .checkIfTextIsNotDisplayedById(R.id.tvEmpty)
            .verifyGreenText("To see the data, please press 'Show Tables'")
            .clickButtonById(R.id.showDataButton)
            .wait(1)
            .on<MenuPage>()
            .checkIfTextIsNotDisplayedById(R.id.tvFull)
            .checkIfListIsDisplayed()
    }

    @Test
    fun click_get_button_and_later_show_button_shows_the_data_and_write_in_edit_text_filter_the_names() {
        on<MenuPage>()
            .wait(1)
            .on<MenuPage>()
            .verifyRedText("Nothing to show, please press 'Get Tables'")
            .clickButtonById(R.id.getTablesDataButton)
            .wait(1)
            .on<MenuPage>()
            .checkIfTextIsNotDisplayedById(R.id.tvEmpty)
            .verifyGreenText("To see the data, please press 'Show Tables'")
            .clickButtonById(R.id.showDataButton)
            .wait(1)
            .on<MenuPage>()
            .checkIfTextIsNotDisplayedById(R.id.tvFull)
            .checkIfListIsDisplayed()
            .clickInEditText()
            .typeTableName()
            .verifyTableNameInRecycler(R.id.recyclerTables, "TableName6")
    }

    @Test
    fun click_get_button_and_later_show_button_shows_the_data_and_search_and_press_hide_button_hide_the_list() {
        on<MenuPage>()
            .wait(1)
            .on<MenuPage>()
            .verifyRedText("Nothing to show, please press 'Get Tables'")
            .clickButtonById(R.id.getTablesDataButton)
            .wait(1)
            .on<MenuPage>()
            .checkIfTextIsNotDisplayedById(R.id.tvEmpty)
            .verifyGreenText("To see the data, please press 'Show Tables'")
            .clickButtonById(R.id.showDataButton)
            .wait(1)
            .on<MenuPage>()
            .verifyIfButtonChangeText("Hide Tables")
            .checkIfTextIsNotDisplayedById(R.id.tvFull)
            .checkIfListIsDisplayed()
            .clickInEditText()
            .typeTableName()
            .verifyTableNameInRecycler(R.id.recyclerTables, "TableName6")
            .clickButtonById(R.id.showDataButton)
            .checkIfListNotDisplayed()
            .verifyGreenText("To see the data, please press 'Show Tables'")
            .verifyIfButtonChangeText("Show Tables")
    }

}
