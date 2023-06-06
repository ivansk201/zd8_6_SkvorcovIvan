package com.example.zad8

import com.google.gson.Gson
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.graphics.drawable.toIcon
import androidx.core.net.toFile

class MenuFilms : AppCompatActivity() {
    private lateinit var filmList: ArrayList<Film>
    private lateinit var listView: ListView
    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageUri: Uri? = null
    private lateinit var sortedFilmList: ArrayList<Film>
    private var selectedFilmPosition: Int = -1
    private lateinit var filteredFilmList: ArrayList<Film>
    private lateinit var originalFilmList: ArrayList<Film>
    private lateinit var filteredFilmAdapter: FilmAdapter
    private lateinit var originalFilmAdapter: FilmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_films)

        originalFilmList = ArrayList()
        filteredFilmList = ArrayList()
        filmList = ArrayList()
        filmList.add(Film("Человек-паук 2", R.drawable.mov1, "Боевик/Фантастика", "Сэм Рэйми", "Питер Паркер возвращается в роли Человека-паука, чтобы сразиться с новыми угрозами, включая злобного Доктора Осьминога. Вместе с проблемами в личной жизни Питера, он должен найти силы, чтобы спасти Нью-Йорк от разрушения."))
        filmList.add(Film("Веном 2", R.drawable.mov2, "Боевик/Фантастика", "Энди Серкис", "Возвращение одного из самых популярных антигероев Вселенной Marvel - Венома. Эдди Брок и его симбиотический супергерой Веном снова объединяются, чтобы сразиться с новой угрозой, порожденной Симбиотической Армией."))
        filmList.add(Film("Железный человек 2", R.drawable.mov3, "Боевик/Фантастика", "Джон Фавро", "Тони Старк, миллиардер и гениальный изобретатель, возвращается в роли Железного Человека. В этом продолжении он сталкивается с новыми врагами и ставит на карту свою жизнь, чтобы защитить мир от угрозы."))
        filmList.add(Film("Новый человек-паук", R.drawable.mov4, "Боевик/Фантастика", "Джон Уоттс", "Молодой Питер Паркер обнаруживает свои новые способности и принимает свое предназначение в роли Человека-паука. Он сталкивается с проблемами подростковой жизни и вступает в схватку с опасными преступниками, чтобы защитить свой город."))
        filmList.add(Film("Новый человек-паук. Высокое напряжение", R.drawable.mov5, "Боевик/Фантастика", "Марк Уэбб", "Питер Паркер продолжает свое приключение в роли Человека-паука, сталкиваясь с новыми вызовами и врагами. Вместе с друзьями он пытается раскрыть секреты своего прошлого и защитить своих близких."))
        filmList.add(Film("Человек-паук. Возвращение домой", R.drawable.mov6, "Боевик/Фантастика", "Джон Уоттс", "Питер Паркер пытается совместить свою двойную жизнь школьника и супергероя Человека-паука. Он отправляется в захватывающее приключение, когда сталкивается с новым злодеем Вапити и его преступной организацией."))
        filmList.add(Film("Железный человек 3", R.drawable.mov7, "Боевик/Фантастика", "Шейн Блэк", "Тони Старк сталкивается с новым вызовом, когда его личная жизнь разрушается и его близкие подвергаются опасности. Он вынужден использовать все свои навыки и свою высокотехнологичную броню, чтобы защитить тех, кто ему дорог."))
        filmList.add(Film("Начало", R.drawable.mov8, "Боевик/Фантастика", "Кристофер Нолан", "Дом Кобб - опытный вор, специалист по краже секретов из снов подсознания. Он получает задание не украсть информацию, а посеять идею в сознании цели, используя метод \"начала\". Сможет ли он выполнить невозможную миссию?"))
        filmList.add(Film("История игрушек", R.drawable.mov14, "Прюключения", "Джон Лассетер", "В мире игрушек Анди, маленького мальчика, игрушки оживают и ведут свою собственную жизнь, когда никто не видит. История следует за приключениями Вуди, Базза и других игрушек, когда они сталкиваются с опасностями и находят свою настоящую ценность."))
        filmList.add(Film("Железный человек", R.drawable.mov9, "Боевик/Фантастика", "Джон Фавро", "Миллиардер и гениальный изобретатель Тони Старк создает мощный костюм Железного Человека, чтобы сразиться с угрозами и злодеями. Он использует свои технологии, чтобы стать героем и защищать мир."))
        filmList.add(Film("Матрица", R.drawable.mov10, "Фантастика", "Лана Вачовски, Лилли Вачовски", "Томас Андерсон, известный как Нео, обнаруживает, что его реальность - всего лишь иллюзия, созданная компьютерной программой. Он вступает в сопротивление и пытается разоблачить и победить машинное господство, которое контролирует человечество."))
        filmList.add(Film("Назад в будущее", R.drawable.mov11, "Фантастика", "Роберт Земекис", "Марти Макфлай путешествует назад во времени на своем экспериментальном автомобиле времени и вступает в приключения с молодым версиями своих родителей. Он должен найти способ вернуться в настоящее время, прежде чем изменит будущее."))
        filmList.add(Film("Аватар", R.drawable.mov12, "Фантастика", "Джеймс Кэмерон", "На планете Пандора существует конфликт между людьми и местным народом Нави. Джейк Салли, бывший морской пехотинец, вступает в мир Нави, используя свое аватарское тело, и становится частью борьбы за выживание этого волшебного мира."))
        filmList.add(Film("Назад в будущее 2", R.drawable.mov13, "Боевик/Фантастика", "Роберт Земекис", "Марти Макфлай и его друг Док Браун отправляются в будущее, чтобы предотвратить катастрофу, но оказываются в замешательстве, когда их действия приводят к изменению их собственного времени. Теперь им приходится исправить ошибки, чтобы вернуться в свое реальное время."))



        listView = findViewById(R.id.listView)
        originalFilmAdapter = FilmAdapter(this, filmList)
        listView.adapter = originalFilmAdapter
        sortedFilmList = ArrayList(filmList)
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedFilm = filmList[position]
            val intent = Intent(this, FilmDetailsActivity::class.java)
            intent.putExtra("film", selectedFilm)
            startActivity(intent)
            selectedFilmPosition=position
        }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add -> {
                showAddFilmDialog()
                return true
            }
            R.id.menu_delete -> {
                if (selectedFilmPosition != -1) {
                    filmList.removeAt(selectedFilmPosition)
                    (listView.adapter as FilmAdapter).notifyDataSetChanged()
                    Toast.makeText(this, "Фильм удален", Toast.LENGTH_SHORT).show()
                    selectedFilmPosition=-1
                } else {
                    Toast.makeText(this, "Выберите фильм для удаления", Toast.LENGTH_SHORT).show()
                }
                return true
            }
            R.id.menu_search -> {
                showSearchDialog()
                return true
            }
            R.id.menu_sort -> {
                filmList.sortBy { it.genre }
                sortedFilmList.sortBy { it.genre }
                listView.adapter = FilmAdapter(this, sortedFilmList)
                (listView.adapter as FilmAdapter).notifyDataSetChanged()

                listView.adapter = FilmAdapter(this, filmList)
                (listView.adapter as FilmAdapter).notifyDataSetChanged()
                Toast.makeText(this, "Сортировка по жанру", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.menu_show_all -> {
                showAllFilms()
                return true
            }
            else -> return super.onOptionsItemSelected(item)

        }
    }
    private fun showAllFilms() {
      recreate()
    }





    fun showMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.menu_films)

        popupMenu.setOnMenuItemClickListener { item ->
            onOptionsItemSelected(item)
        }

        popupMenu.show()
    }



    private fun showAddFilmDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_add_film)
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

        val etTitle = dialog.findViewById<EditText>(R.id.et_title)
        val etGenre = dialog.findViewById<EditText>(R.id.et_genre)
        val etAutoh = dialog.findViewById<EditText>(R.id.et_autor)
        val etOpsaine = dialog.findViewById<EditText>(R.id.et_sumary)
        val btnAdd = dialog.findViewById<Button>(R.id.btn_add)
        val btnAddPhoto = dialog.findViewById<Button>(R.id.btn_choose_photo)

        btnAddPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        btnAdd.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val genre = etGenre.text.toString().trim()
            val autor = etAutoh.text.toString().trim()
            val opsanie = etOpsaine.text.toString().trim()

            if (title.isNotEmpty() && genre.isNotEmpty()) {
                val newFilm = Film(title, R.drawable.nofoto, genre, autor, opsanie)
                filmList.add(newFilm)
                (listView.adapter as FilmAdapter).notifyDataSetChanged()
                Toast.makeText(this, "Фильм добавлен", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Введите название и жанр фильма, и выберите фотографию", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun showSearchDialog() {

        val searchDialog = Dialog(this)
        searchDialog.setContentView(R.layout.dialog_search)
        searchDialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

        val etSearch = searchDialog.findViewById<EditText>(R.id.et_search)
        val btnSearch = searchDialog.findViewById<Button>(R.id.btn_search)

        btnSearch.setOnClickListener {
            val searchQuery = etSearch.text.toString().trim()
            filterFilmList(searchQuery)

            searchDialog.dismiss()
        }

        searchDialog.show()
    }

    private fun filterFilmList(searchQuery: String) {
        filteredFilmList.clear()

        for (film in filmList) {
            if (film.title.contains(searchQuery, ignoreCase = true)) {
                filteredFilmList.add(film)
            }
        }

        if (filteredFilmList.isNotEmpty()) {
            filteredFilmAdapter = FilmAdapter(this, filteredFilmList)
            listView.adapter = filteredFilmAdapter
            filteredFilmAdapter.notifyDataSetChanged()

            filmList= ArrayList(filteredFilmList)
        } else {
            listView.adapter = FilmAdapter(this, filmList)
            (listView.adapter as FilmAdapter).notifyDataSetChanged()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data

        }
    }

}
