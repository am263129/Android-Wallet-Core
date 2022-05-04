package com.trust.walletclone.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.trust.walletclone.activity.BrowserActivity
import com.trust.walletclone.adapter.DappAdapter
import com.trust.walletclone.databinding.FragmentDappBinding
import com.trust.walletclone.util.Dapp

class DappFragment : Fragment() {
    private lateinit var binding: FragmentDappBinding
    var newDappList :ArrayList<Dapp> = ArrayList()
    var defiDappList :ArrayList<Dapp> = ArrayList()
    var popularDappList :ArrayList<Dapp> = ArrayList()
    var smartChainDappList :ArrayList<Dapp> = ArrayList()
    var exchangeDappList :ArrayList<Dapp> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDappBinding.inflate(inflater)
        initView()
        // Inflate the layout for this fragment
        return binding.root
    }

    fun initView(){
        newDappList.add(Dapp("LooksRare","https://looksrare.org","LooksRare is a next generation NFT market. Buy NFTs, sell NFTs... or just HODL:","looksrare.png","new"))
        newDappList.add(Dapp("MCDEX","https://app.mcdex.io","Permissionless decentralized perpetual protocol","mcdex.png","new"))
        newDappList.add(Dapp("WazirX NFT","https://nft.wazirx.org","Create & Collect Timeless NFTs. NFT marketplace made Simpler","wazirx.png","new"))
        newDappList.add(Dapp("Refinable","https://looksrare.org","Refinable is the definitive solution to create, discover, trade, and leverate NFTs","refinable.png","new"))
        newDappList.add(Dapp("PowerPool","https://looksrare.org","PowerPool is a DAO manage of non-custodial structured investments.","powerpool.png","new"))
        newDappList.add(Dapp("Tranchess","https://looksrare.org","Tranchess is a tokenized asset management and derivatives trading protocol","tranchess.png","new"))
        newDappList.add(Dapp("Venus","https://looksrare.org","A Decentralized Marketplace for Lenders and Borrowers with Borderless Stablecoins","venus.png","new"))
        newDappList.add(Dapp("Beefy Finance","https://looksrare.org","Yield farming optimizer for Binance Smart Chain","beefy.png","new"))
        newDappList.add(Dapp("PancakeSwap","https://looksrare.org","The flippening is coming. Stake CAKE on Binance Smart Chain","pancake.png","new"))

        defiDappList.add(Dapp("PancakeSwap","https://looksrare.org","The flippening is coming. Stake CAKE on Binance Smart Chain","pancake.png","defi"))
        defiDappList.add(Dapp("Refinable","https://looksrare.org","Refinable is the definitive solution to create, discover, trade, and leverate NFTs","refinable.png","defi"))
        defiDappList.add(Dapp("Venus","https://looksrare.org","A Decentralized Marketplace for Lenders and Borrowers with Borderless Stablecoins","venus.png","defi"))
        defiDappList.add(Dapp("Beefy Finance","https://looksrare.org","Yield farming optimizer for Binance Smart Chain","beefy.png","defi"))
        defiDappList.add(Dapp("PowerPool","https://looksrare.org","PowerPool is a DAO manage of non-custodial structured investments.","powerpool.png","defi"))
        defiDappList.add(Dapp("Tranchess","https://looksrare.org","Tranchess is a tokenized asset management and derivatives trading protocol","tranchess.png","defi"))
        defiDappList.add(Dapp("LooksRare","https://looksrare.org","LooksRare is a next generation NFT market. Buy NFTs, sell NFTs... or just HODL:","looksrare.png","defi"))
        defiDappList.add(Dapp("MCDEX","https://app.mcdex.io","Permissionless decentralized perpetual protocol","mcdex.png","defi"))
        defiDappList.add(Dapp("WazirX NFT","https://nft.wazirx.org","Create & Collect Timeless NFTs. NFT marketplace made Simpler","wazirx.png","defi"))

        smartChainDappList.add(Dapp("PowerPool","https://looksrare.org","PowerPool is a DAO manage of non-custodial structured investments.","powerpool.png","sc"))
        smartChainDappList.add(Dapp("Tranchess","https://looksrare.org","Tranchess is a tokenized asset management and derivatives trading protocol","tranchess.png","sc"))
        smartChainDappList.add(Dapp("Venus","https://looksrare.org","A Decentralized Marketplace for Lenders and Borrowers with Borderless Stablecoins","venus.png","sc"))
        smartChainDappList.add(Dapp("Beefy Finance","https://looksrare.org","Yield farming optimizer for Binance Smart Chain","beefy.png","sc"))
        smartChainDappList.add(Dapp("PancakeSwap","https://looksrare.org","The flippening is coming. Stake CAKE on Binance Smart Chain","pancake.png","sc"))
        smartChainDappList.add(Dapp("LooksRare","https://looksrare.org","LooksRare is a next generation NFT market. Buy NFTs, sell NFTs... or just HODL:","looksrare.png","sc"))
        smartChainDappList.add(Dapp("MCDEX","https://app.mcdex.io","Permissionless decentralized perpetual protocol","mcdex.png","sc"))
        smartChainDappList.add(Dapp("WazirX NFT","https://nft.wazirx.org","Create & Collect Timeless NFTs. NFT marketplace made Simpler","wazirx.png","sc"))
        smartChainDappList.add(Dapp("Refinable","https://looksrare.org","Refinable is the definitive solution to create, discover, trade, and leverate NFTs","refinable.png","sc"))

        popularDappList.add(Dapp("WazirX NFT","https://nft.wazirx.org","Create & Collect Timeless NFTs. NFT marketplace made Simpler","wazirx.png","popular"))
        popularDappList.add(Dapp("Refinable","https://looksrare.org","Refinable is the definitive solution to create, discover, trade, and leverate NFTs","refinable.png","popular"))
        popularDappList.add(Dapp("PowerPool","https://looksrare.org","PowerPool is a DAO manage of non-custodial structured investments.","powerpool.png","popular"))
        popularDappList.add(Dapp("Tranchess","https://looksrare.org","Tranchess is a tokenized asset management and derivatives trading protocol","tranchess.png","popular"))
        popularDappList.add(Dapp("Venus","https://looksrare.org","A Decentralized Marketplace for Lenders and Borrowers with Borderless Stablecoins","venus.png","popular"))
        popularDappList.add(Dapp("Beefy Finance","https://looksrare.org","Yield farming optimizer for Binance Smart Chain","beefy.png","popular"))
        popularDappList.add(Dapp("PancakeSwap","https://looksrare.org","The flippening is coming. Stake CAKE on Binance Smart Chain","pancake.png","popular"))
        popularDappList.add(Dapp("LooksRare","https://looksrare.org","LooksRare is a next generation NFT market. Buy NFTs, sell NFTs... or just HODL:","looksrare.png","popular"))
        popularDappList.add(Dapp("MCDEX","https://app.mcdex.io","Permissionless decentralized perpetual protocol","mcdex.png","popular"))

        exchangeDappList.add(Dapp("Tranchess","https://looksrare.org","Tranchess is a tokenized asset management and derivatives trading protocol","tranchess.png","exchange"))
        exchangeDappList.add(Dapp("Venus","https://looksrare.org","A Decentralized Marketplace for Lenders and Borrowers with Borderless Stablecoins","venus.png","exchange"))
        exchangeDappList.add(Dapp("Beefy Finance","https://looksrare.org","Yield farming optimizer for Binance Smart Chain","beefy.png","exchange"))
        exchangeDappList.add(Dapp("PancakeSwap","https://looksrare.org","The flippening is coming. Stake CAKE on Binance Smart Chain","pancake.png","exchange"))
        exchangeDappList.add(Dapp("LooksRare","https://looksrare.org","LooksRare is a next generation NFT market. Buy NFTs, sell NFTs... or just HODL:","looksrare.png","exchange"))
        exchangeDappList.add(Dapp("MCDEX","https://app.mcdex.io","Permissionless decentralized perpetual protocol","mcdex.png","exchange"))
        exchangeDappList.add(Dapp("WazirX NFT","https://nft.wazirx.org","Create & Collect Timeless NFTs. NFT marketplace made Simpler","wazirx.png","exchange"))
        exchangeDappList.add(Dapp("Refinable","https://looksrare.org","Refinable is the definitive solution to create, discover, trade, and leverate NFTs","refinable.png","exchange"))
        exchangeDappList.add(Dapp("PowerPool","https://looksrare.org","PowerPool is a DAO manage of non-custodial structured investments.","powerpool.png","exchange"))


        binding.listNewDapp.layoutManager = LinearLayoutManager(context)
        binding.listDefi.layoutManager = LinearLayoutManager(context)
        binding.listPopular.layoutManager = LinearLayoutManager(context)
        binding.listSmartChain.layoutManager = LinearLayoutManager(context)
        binding.listExchange.layoutManager = LinearLayoutManager(context)

        var newDappAdapter = DappAdapter(newDappList, context)
        newDappAdapter.onItemClick = { dapp ->
            val intent = Intent(context, BrowserActivity::class.java)
            intent.putExtra("URL",dapp.url)
            startActivity(intent)
        }
        binding.listNewDapp.adapter = newDappAdapter
        binding.listDefi.adapter = DappAdapter(defiDappList, context)
        binding.listPopular.adapter = DappAdapter(popularDappList, context)
        binding.listSmartChain.adapter = DappAdapter(smartChainDappList, context)
        binding.listExchange.adapter = DappAdapter(exchangeDappList, context)

        binding.historyContainer.visibility = View.GONE

    }
}