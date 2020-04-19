/*
Ovde ima nekoliko situacija i refaktorisanja
1.  Osnovna pre klase ViewHolder
2.  Prvobitna sa ViewHolderom
3.  A-REFACTOR nekoliko koraka refaktorisanja onBindViewHolder metode
4.  B-REFACTOR nekoliko koraka refaktorisanja onCreateViewHolder metode
5.  DIFF-UTIL REFACTOR
6.  DATA BINDING REFACTOR
7.  BINDING ADAPTER - BINDING UTILS
 */

package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding

// PRE KREIRANJA VIEW_HOLDER KLASE
// class SleepNightAdapter: RecyclerView.Adapter<TextViewItemHolder>() {
// PRE DIFF-UTIL REFACTOR
// class SleepNightAdapter: RecyclerView.Adapter<SleepNightAdapter.ViewHolder>() {
class SleepNightAdapter: ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {

    // DIFF-UTIL REFACTOR 1 - ne treba mi vise data i seter jer ce adapter lista sad voditi racuna o promenama
//    // podaci za tabelu, koje cemo popuniti iz baze
//    var data = listOf<SleepNight>()
//        set(value) { // moram da dodam i ovaj seter, koji se ponasa kao Swiftov didSet zbog notifyDataSetChanged(), a razlog je obavestavanje Recyclera kad god se desi promena
//            field = value
//            notifyDataSetChanged()
//        }

    // DIFF-UTIL REFACTOR 2 - ne treba mi vise getItemCount jer ce adapter lista raditi prebrojavanje
//    // u Swiftu je ovo func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int
//    override fun getItemCount(): Int = data.size

    // u Swiftu je ovo func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell
//    override fun onBindViewHolder(holder: TextitemViewHolder, position: Int) { // PRE KREIRANJA KLASE VIEW_HOLDER KLASE
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // DIFF-UTIL REFACTOR 3 - koristicu list adapter metodu getItem
//        val item = data[position]
        val item = getItem(position)

        // A-REFAKTOR 4 - OnBind, ovo prenosim u ViewHolder klasu u bind metodu
//        val res = holder.itemView.context.resources

        // A-REFAKTOR 1 - OnBind, oznacim ovo sto je ispod zakomentarsano, desni klik/ Refactor/ Extract/ Function i dobijem izdvojenu BIND metodu
//        holder.sleepQuality.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
//        holder.qualityImage.setImageResource(when(item.sleepQuality) {
//            0 -> R.drawable.ic_sleep_0
//            1 -> R.drawable.ic_sleep_1
//            2 -> R.drawable.ic_sleep_2
//            3 -> R.drawable.ic_sleep_3
//            4 -> R.drawable.ic_sleep_4
//            5 -> R.drawable.ic_sleep_5
//            else -> R.drawable.ic_sleep_active
//        })

        // A-REFAKTOR 2 - OnBind
//        bind(holder, item, res)
        // A-REFAKTOR 3 - OnBind
//        holder.bind(item, res)
        // A-REFAKTOR 4 - OnBind
        holder.bind(item)
    }

    // A-REFAKTOR 2 - OnBind, oznacim paramtear holder, na tastatrui Option + Enter, i onda odaberem Convert Parameter Receiver
//    private fun bind(holder: ViewHolder, item: SleepNight, res: Resources) {
//        holder.sleepQuality.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
//        holder.qualityImage.setImageResource(when (item.sleepQuality) {
//            0 -> R.drawable.ic_sleep_0
//            1 -> R.drawable.ic_sleep_1
//            2 -> R.drawable.ic_sleep_2
//            3 -> R.drawable.ic_sleep_3
//            4 -> R.drawable.ic_sleep_4
//            5 -> R.drawable.ic_sleep_5
//            else -> R.drawable.ic_sleep_active
//        })
//    }

    // A-REFAKTOR 3 - OnBind, oznacim sve od .bind i uradim cut/paste u ViewHolder klasu, a ovde izbrisem bind metodu
//    private fun ViewHolder.  ****   bind(item: SleepNight, res: Resources) {
//        sleepQuality.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
//        qualityImage.setImageResource(when (item.sleepQuality) {
//            0 -> R.drawable.ic_sleep_0
//            1 -> R.drawable.ic_sleep_1
//            2 -> R.drawable.ic_sleep_2
//            3 -> R.drawable.ic_sleep_3
//            4 -> R.drawable.ic_sleep_4
//            5 -> R.drawable.ic_sleep_5
//            else -> R.drawable.ic_sleep_active
//        })
//    }

    // OVAJ ISCRTAVA CELIJE - onCreateViewHolder
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewItemHolder { // PRE KREIRANJA KLASE VIEW_HOLDER KLASE
    // B-REFAKTOR 1 - onCreateViewHolder - oznacim sve i ekstrakujem kao FROM metodu
    //    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    //        val layoutInflater = LayoutInflater.from(parent.context)
    //        // val view = layoutInflater.inflate(R.layout.text_item_view, parent, false) as TextView // PRE KREIRANJA KLASE VIEW_HOLDER KLASE
    //        // posto sam napravio layout za celiju koju cu da korstim, tj list_item_sleep_night gornji  red mi ne treba vise
    //        val view = layoutInflater.inflate(R.layout.list_item_sleep_night, parent, false)
    //        // return TextViewItemHolder(view) // PRE KREIRANJA KLASE VIEW_HOLDER KLASE i NJENOG LAYOUTA
    //        return ViewHolder(view)
    //    }
    // B-REFAKTOR 1 - onCreateViewHolder
    //    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    //        return from(parent)
    //    }
    // B-REFAKTOR 2 - onCreateViewHolder
    //    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    //        return Companion.from(parent)
    //    }
    // B-REFAKTOR 4 - onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    // B-REFAKTOR 2 - onCreateViewHolder - option + enter i biram Move to companion object
    //    fun from(parent: ViewGroup): ViewHolder {
    //        val layoutInflater = LayoutInflater.from(parent.context)
    //        val view = layoutInflater.inflate(R.layout.list_item_sleep_night, parent, false)
    //        return ViewHolder(view)
    //    }
    // B-REFAKTOR 3 - onCreateViewHolder - cut/paste u ViewHolder klasu
    //    companion object {
    //        fun from(parent: ViewGroup): ViewHolder {
    //            val layoutInflater = LayoutInflater.from(parent.context)
    //            val view = layoutInflater.inflate(R.layout.list_item_sleep_night, parent, false)
    //            return ViewHolder(view)
    //        }
    //    }


    // Pravim klasu koja ce predstavlja celiju, unutar adapter klase
    // B-REFAKTOR 4 - onCreateViewHolder - pravim private kontruktor   //prethodno ->//  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//    class ViewHolder private constructor (itemView: ListItemSleepNightBinding) : RecyclerView.ViewHolder(itemView) {
    // DATA BINDING REFACTOR
    class ViewHolder private constructor (val binding: ListItemSleepNightBinding) : RecyclerView.ViewHolder(binding.root) {
        // DATA BINDING REFACTOR
//        val sleepQuality: TextView = itemView.findViewById(R.id.sleepLenght)
//        val quality: TextView = itemView.findViewById(R.id.qualityText)
//        var qualityImage: ImageView = itemView.findViewById(R.id.qualityImage)
        // DATA BINDING REFACTOR dodatni korak za INLINE - sve ih sklanja u bind metodu
//        val sleepQuality: TextView = binding.sleepLenght
//        val quality: TextView = binding.qualityText
//        var qualityImage: ImageView = binding.qualityImage

        // A-REFAKTOR 3 - OnBind - cut/paste funkciju bind ovde
        // A-REFAKTOR 4 - OnBind - brisem res argument iz potpisa metode  // fun bind(item: SleepNight, res: Resources)
        fun bind(item: SleepNight) {
//            // A-REFAKTOR 4 - OnBind dodajem val res iz onBindViewHolder i brisem ga kao parametar metode
//            val res = itemView.context.resources
//
//            // DATA BINDING REFACTOR dodao binding ispred sleepLenght
//            binding.sleepLenght.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
//            // DATA BINDING REFACTOR dodao binding ispred quality
//            binding.qualityText.text = convertNumericQualityToString(item.sleepQuality, res)
//            // DATA BINDING REFACTOR dodao binding ispred qualityImage
//            binding.qualityImage.setImageResource(when (item.sleepQuality) {
//                0 -> R.drawable.ic_sleep_0
//                1 -> R.drawable.ic_sleep_1
//                2 -> R.drawable.ic_sleep_2
//                3 -> R.drawable.ic_sleep_3
//                4 -> R.drawable.ic_sleep_4
//                5 -> R.drawable.ic_sleep_5
//                else -> R.drawable.ic_sleep_active
//            })

            // BINDING ADAPTER - BINDING UTILS
            binding.sleep = item // sleep predstavlja model baze i povlaci se iz data taga ViewHolder layouta
            binding.executePendingBindings() // ovo ubrzava proces bindovanja

        }
        // B-REFAKTOR 3 - onCreateViewHolder
        companion object {
             fun from(parent: ViewGroup): ViewHolder {
                 val layoutInflater = LayoutInflater.from(parent.context)
                 // DATA BINDING REFACTOR 1
                 // val view = layoutInflater.inflate(R.layout.list_item_sleep_night, parent, false)
                 val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}

// Poboljsanje komunikacije Recyclera sa promenom podataka i glatko prikazivanje UI-ja
class SleepNightDiffCallback: DiffUtil.ItemCallback<SleepNight>() {
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.nightId == newItem.nightId
    }

    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem == newItem
    }

}