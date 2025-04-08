package com.example.taipeizoo.fragment

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.taipeizoo.R
import com.example.taipeizoo.databinding.FragmentInfoBottomSheetBinding
import com.example.taipeizoo.network.model.ZooItem
import com.example.taipeizoo.replaceHttp
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InfoBottomSheetFragment : BottomSheetDialogFragment() {
    companion object {
        fun newInstance(zooItem: ZooItem): InfoBottomSheetFragment {
            val fragment = InfoBottomSheetFragment()
            val args = Bundle()
            args.putParcelable("zooItem", zooItem)
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentInfoBottomSheetBinding? = null
    private val binding get() = _binding!!
    private var mZooItem: ZooItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mZooItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("zooItem", ZooItem::class.java)
        } else {
            arguments?.getParcelable("zooItem")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mZooItem?.apply {
            binding.tvTitle.text = nameCh
            Glide.with(this@InfoBottomSheetFragment).load(pic01Url.replaceHttp).placeholder(R.drawable.logo).into(binding.ivImg)
            val info = StringBuilder()
            if (type == "ANIMAL") {
                info.append("${nameCh}\n")
                info.append(nameEn)
                if (alsoKnown.isNotEmpty()) {
                    info.append("\n\n")
                    info.append("${getString(R.string.also_known)}\n")
                    info.append(alsoKnown)
                }
                if (phylum.isNotEmpty()) {
                    info.append("\n\n")
                    info.append("${getString(R.string.phylum)}\n")
                    info.append(phylum)
                    if (animalClass.isNotEmpty()) {
                        info.append(">")
                        info.append(animalClass)
                        if (order.isNotEmpty()) {
                            info.append(">")
                            info.append(order)
                            if (family.isNotEmpty()) {
                                info.append(">")
                                info.append(family)
                            }
                        }
                    }
                }
                if (distribution.isNotEmpty()) {
                    info.append("\n\n")
                    info.append("${getString(R.string.distribution)}\n")
                    info.append(distribution)
                }
                if (feature.isNotEmpty()) {
                    info.append("\n\n")
                    info.append("${getString(R.string.feature)}\n")
                    info.append(feature)
                }
                if (behavior.isNotEmpty()) {
                    info.append("\n\n")
                    info.append("${getString(R.string.behavior)}\n")
                    info.append(behavior)
                }
                if (update.isNotEmpty()) {
                    info.append("\n\n")
                    info.append(getString(R.string.last_update, update))
                }
            } else {
                info.append("${nameCh}\n")
                info.append(nameEn)
                if (alsoKnown.isNotEmpty()) {
                    info.append("\n\n")
                    info.append("${getString(R.string.also_known)}\n")
                    info.append(alsoKnown)
                }
                if (brief.isNotEmpty()) {
                    info.append("\n\n")
                    info.append("${getString(R.string.brief)}\n")
                    info.append(brief)
                }
                if (feature.isNotEmpty()) {
                    info.append("\n\n")
                    info.append("${getString(R.string.plant_feature)}\n")
                    info.append(feature)
                }
                if (functionApplication.isNotEmpty()) {
                    info.append("\n\n")
                    info.append("${getString(R.string.function_application)}\n")
                    info.append(functionApplication)
                }
                if (update.isNotEmpty()) {
                    info.append("\n\n")
                    info.append(getString(R.string.last_update, update))
                }
            }
            binding.tvInfo.text = info.toString()
        }
    }

    override fun onStart() {
        super.onStart()
        binding.root.rootView.post {
            val d = dialog as? BottomSheetDialog ?: return@post
            val bottomSheet: View? = d.findViewById(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)

                it.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                it.requestLayout()

                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.peekHeight = 0
                behavior.skipCollapsed = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Glide.with(this@InfoBottomSheetFragment).clear(binding.ivImg)
        _binding = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireActivity(), R.style.Material3BottomSheetDialogRoundCorner)
    }
}