package com.scsinfinity.udhd.configurations.dbinitializers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.scsinfinity.udhd.dao.entities.base.UserGroupEntity;
import com.scsinfinity.udhd.dao.entities.geography.GeoDistrictEntity;
import com.scsinfinity.udhd.dao.entities.geography.GeoDivisionEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBType;
import com.scsinfinity.udhd.dao.entities.ledger.BaseHeadEntity;
import com.scsinfinity.udhd.dao.repositories.base.IUserGroupRepository;
import com.scsinfinity.udhd.dao.repositories.geography.IGeoDistrictRepository;
import com.scsinfinity.udhd.dao.repositories.geography.IGeoDivisionRepository;
import com.scsinfinity.udhd.dao.repositories.geography.IULBRepository;
import com.scsinfinity.udhd.dao.repositories.ledger.IBaseHeadRepository;

import lombok.AllArgsConstructor;

//@Component
@AllArgsConstructor
public class ULBInitializer {

	private final IGeoDivisionRepository divisionRepo;
	private final IGeoDistrictRepository districtRepo;
	private final IULBRepository ulbRepo;
	private final IBaseHeadRepository baseHeadRepo;
	private final IUserGroupRepository userGrpRepo;

	public void doActions() {
		//saveDivision();
		//saveDistrict();
		//saveULB();
	}

	private void saveDivision() {
		divisionRepo.save(new GeoDivisionEntity("Patna", "Pat", true));
		divisionRepo.save(new GeoDivisionEntity("Magadh", "magadh", true));
		divisionRepo.save(new GeoDivisionEntity("Bhagalpur", "Bha", true));

		divisionRepo.save(new GeoDivisionEntity("Tirhut", "tir", true));
		divisionRepo.save(new GeoDivisionEntity("Darbhanga", "Dar", true));
		divisionRepo.save(new GeoDivisionEntity("Munger", "munger", true));

		divisionRepo.save(new GeoDivisionEntity("Purnia", "purnia", true));
		divisionRepo.save(new GeoDivisionEntity("Saran", "saran", true));
		divisionRepo.save(new GeoDivisionEntity("Koshi", "koshi", true));
	}

	private void saveDistrict() {
		Optional<GeoDivisionEntity> divPatnaO = divisionRepo.findByName("Patna");
		if (divPatnaO.isPresent()) {
			GeoDivisionEntity divPatna = divPatnaO.get();

			districtRepo.save(new GeoDistrictEntity("Patna", "Patna", true, divPatna));
			districtRepo.save(new GeoDistrictEntity("Bhojpur", "bhojpur", true, divPatna));
			districtRepo.save(new GeoDistrictEntity("Rohtas", "rohtas", true, divPatna));
			districtRepo.save(new GeoDistrictEntity("Kaimur", "kaimur", true, divPatna));
			districtRepo.save(new GeoDistrictEntity("Nalanda", "nalanda", true, divPatna));
			districtRepo.save(new GeoDistrictEntity("Buxar", "buxar", true, divPatna));

		}
		Optional<GeoDivisionEntity> divMagadhO = divisionRepo.findByName("Magadh");
		if (divMagadhO.isPresent()) {
			GeoDivisionEntity divMagadh = divMagadhO.get();
			districtRepo.save(new GeoDistrictEntity("Gaya", "gaya", true, divMagadh));
			districtRepo.save(new GeoDistrictEntity("Jehanabad", "Jehanabad", true, divMagadh));
			districtRepo.save(new GeoDistrictEntity("Aurangabad", "Aurangabad", true, divMagadh));
			districtRepo.save(new GeoDistrictEntity("Nawada", "nawada", true, divMagadh));
			districtRepo.save(new GeoDistrictEntity("Arwal", "arwal", true, divMagadh));

		}

		Optional<GeoDivisionEntity> divBhagalpurO = divisionRepo.findByName("Bhagalpur");
		if (divBhagalpurO.isPresent()) {
			GeoDivisionEntity divBhagalpur = divBhagalpurO.get();
			districtRepo.save(new GeoDistrictEntity("Bhagalpur", "bhagalpur", true, divBhagalpur));
			districtRepo.save(new GeoDistrictEntity("Banka", "banka", true, divBhagalpur));
		}

		Optional<GeoDivisionEntity> divTirhutO = divisionRepo.findByName("Tirhut");
		if (divTirhutO.isPresent()) {
			GeoDivisionEntity divTirhut = divTirhutO.get();

			districtRepo.save(new GeoDistrictEntity("Sitamarhi", "sitamarhi", true, divTirhut));
			districtRepo.save(new GeoDistrictEntity("Muzaffarpur", "muzaffar", true, divTirhut));

			districtRepo.save(new GeoDistrictEntity("Vaishali", "vaishali", true, divTirhut));
			districtRepo.save(new GeoDistrictEntity("Sheohar", "sheohar", true, divTirhut));

			districtRepo.save(new GeoDistrictEntity("East Champaran", "purvi", true, divTirhut));
			districtRepo.save(new GeoDistrictEntity("West Champaran", "WestChamp", true, divTirhut));

		}

		Optional<GeoDivisionEntity> divDarbhangaO = divisionRepo.findByName("Darbhanga");
		if (divDarbhangaO.isPresent()) {
			GeoDivisionEntity divDarbhanga = divDarbhangaO.get();

			districtRepo.save(new GeoDistrictEntity("Madhubani", "madhubani", true, divDarbhanga));
			districtRepo.save(new GeoDistrictEntity("Samastipur", "samastipur", true, divDarbhanga));
			districtRepo.save(new GeoDistrictEntity("Darbhanga", "darbhanga", true, divDarbhanga));
		}

		Optional<GeoDivisionEntity> divMungerO = divisionRepo.findByName("Munger");
		if (divMungerO.isPresent()) {
			GeoDivisionEntity divMunger = divMungerO.get();
			districtRepo.save(new GeoDistrictEntity("Munger", "munger", true, divMunger));
			districtRepo.save(new GeoDistrictEntity("Lakhisarai", "lakhisarai", true, divMunger));
			districtRepo.save(new GeoDistrictEntity("Jamui", "jamui", true, divMunger));
			districtRepo.save(new GeoDistrictEntity("Khagaria", "khagaria", true, divMunger));
			districtRepo.save(new GeoDistrictEntity("Begusarai", "begusarai", true, divMunger));
			districtRepo.save(new GeoDistrictEntity("Sheikhpura", "sheikhpura", true, divMunger));

		}

		Optional<GeoDivisionEntity> divSaranO = divisionRepo.findByName("Saran");
		if (divSaranO.isPresent()) {
			GeoDivisionEntity divSaran = divSaranO.get();
			districtRepo.save(new GeoDistrictEntity("Saran", "saran", true, divSaran));
			districtRepo.save(new GeoDistrictEntity("Siwan", "siwan", true, divSaran));
			districtRepo.save(new GeoDistrictEntity("Gopalganj", "gopalganj", true, divSaran));

		}

		Optional<GeoDivisionEntity> divKoshiO = divisionRepo.findByName("Koshi");
		if (divKoshiO.isPresent()) {
			GeoDivisionEntity divKoshi = divKoshiO.get();
			districtRepo.save(new GeoDistrictEntity("Supaul", "supaul", true, divKoshi));
			districtRepo.save(new GeoDistrictEntity("Saharsa", "saharsa", true, divKoshi));
			districtRepo.save(new GeoDistrictEntity("Madhepura", "madhepura", true, divKoshi));
		}

		Optional<GeoDivisionEntity> divPurniyaO = divisionRepo.findByName("Purnia");
		if (divPurniyaO.isPresent()) {
			GeoDivisionEntity divPurniya = divPurniyaO.get();
			districtRepo.save(new GeoDistrictEntity("Purnia", "purnia", true, divPurniya));
			districtRepo.save(new GeoDistrictEntity("Araria", "araria", true, divPurniya));
			districtRepo.save(new GeoDistrictEntity("Kishanganj", "kishanganj", true, divPurniya));
			districtRepo.save(new GeoDistrictEntity("Katihar", "katihar", true, divPurniya));

		}

	}

	private void saveULB() {

		List<BaseHeadEntity> baseHeads = baseHeadRepo.findAll();
		Optional<GeoDistrictEntity> disPatnaO = districtRepo.findByNameAndDivision_Name("Patna", "Patna");
		if (disPatnaO.isPresent()) {

			GeoDistrictEntity disPatna = disPatnaO.get();
			ULBEntity ulbPatna = ulbRepo.save(getULB("Patna Nagar Nigam", "ulbpatna", "ulbpatna",
					ULBType.MUNICIPAL_CORPORATION, disPatna, baseHeads, createULBUserGroup("Patna Nagar Nigam")));
			ulbRepo.save(ulbPatna);
		}

		Optional<GeoDistrictEntity> disNalandaO = districtRepo.findByNameAndDivision_Name("Nalanda", "Patna");
		if (disNalandaO.isPresent()) {
			GeoDistrictEntity disNalanda = disNalandaO.get();
			ULBEntity ulbNalanda = ulbRepo
					.save(getULB("BiharSarif Nagar Nigam", "ulbsarif", "ulbsarif", ULBType.MUNICIPAL_CORPORATION,
							disNalanda, baseHeads, createULBUserGroup("BiharSarif Nagar Nigam")));
			ulbRepo.save(ulbNalanda);
		}

		Optional<GeoDistrictEntity> disBhojpurO = districtRepo.findByNameAndDivision_Name("Bhojpur", "Patna");
		if (disBhojpurO.isPresent()) {
			GeoDistrictEntity disBhojpur = disBhojpurO.get();
			ULBEntity ulbAra = ulbRepo.save(getULB("Arra Nagar Nigam", "ulbarra", "ulbarra",
					ULBType.MUNICIPAL_CORPORATION, disBhojpur, baseHeads, createULBUserGroup("Arra Nagar Nigam")));
			ulbRepo.save(ulbAra);

		}

		Optional<GeoDistrictEntity> disGayaO = districtRepo.findByNameAndDivision_Name("Gaya", "Magadh");
		if (disGayaO.isPresent()) {
			GeoDistrictEntity disGaya = disGayaO.get();
			ULBEntity ulbGaya = ulbRepo.save(getULB("Gaya Nagar Nigam", "ulbgaya", "ulbgaya",
					ULBType.MUNICIPAL_CORPORATION, disGaya, baseHeads, createULBUserGroup("Gaya Nagar Nigam")));
			ulbRepo.save(ulbGaya);
		}

		Optional<GeoDistrictEntity> disBhagalpurO = districtRepo.findByNameAndDivision_Name("Bhagalpur", "Bhagalpur");
		if (disBhagalpurO.isPresent()) {
			GeoDistrictEntity disBhagalpur = disBhagalpurO.get();
			ULBEntity ulbBhagalpur = ulbRepo
					.save(getULB("Bhagalpur Nagar Nigam", "bhagalpur", "ulbbhagalpur", ULBType.MUNICIPAL_CORPORATION,
							disBhagalpur, baseHeads, createULBUserGroup("Bhagalpur Nagar Nigam")));
			ulbRepo.save(ulbBhagalpur);

		}

		Optional<GeoDistrictEntity> disMuzaffarpurO = districtRepo.findByNameAndDivision_Name("Muzaffarpur", "Tirhut");
		if (disMuzaffarpurO.isPresent()) {
			GeoDistrictEntity disMuzaffarpur = disMuzaffarpurO.get();
			ULBEntity ulbMuzaffar = ulbRepo
					.save(getULB("Muzaffarpur Nagar Nigam", "muzafarpur", "Muzaffarpur", ULBType.MUNICIPAL_CORPORATION,
							disMuzaffarpur, baseHeads, createULBUserGroup("Muzaffarpur Nagar Nigam")));
			ulbRepo.save(ulbMuzaffar);
		}

		Optional<GeoDistrictEntity> disDarbhangaO = districtRepo.findByNameAndDivision_Name("Darbhanga", "Darbhanga");
		if (disDarbhangaO.isPresent()) {
			GeoDistrictEntity disDarbhanga = disDarbhangaO.get();
			ULBEntity ulbDarbhanga = ulbRepo
					.save(getULB("Darbhanga Nagar Nigam", "darbhanga", "darbhanga", ULBType.MUNICIPAL_CORPORATION,
							disDarbhanga, baseHeads, createULBUserGroup("Darbhanga Nagar Nigam")));
			ulbRepo.save(ulbDarbhanga);

		}

		Optional<GeoDistrictEntity> disBegusaraiO = districtRepo.findByNameAndDivision_Name("Begusarai", "Munger");
		if (disBegusaraiO.isPresent()) {
			GeoDistrictEntity disBegusarai = disBegusaraiO.get();
			ULBEntity ulbBegusarai = ulbRepo
					.save(getULB("Begusarai Nagar Nigam", "ulbbegusarai", "ulbbegusarai", ULBType.MUNICIPAL_CORPORATION,
							disBegusarai, baseHeads, createULBUserGroup("Begusarai Nagar Nigam")));
			ulbRepo.save(ulbBegusarai);

		}

		Optional<GeoDistrictEntity> disMungerO = districtRepo.findByNameAndDivision_Name("Munger", "Munger");
		if (disMungerO.isPresent()) {
			GeoDistrictEntity disMunger = disMungerO.get();
			ULBEntity ulbMunger = ulbRepo.save(getULB("Munger Nagar Nigam", "ulbMunger", "ulbMunger",
					ULBType.MUNICIPAL_CORPORATION, disMunger, baseHeads, createULBUserGroup("Munger Nagar Nigam")));
			ulbRepo.save(ulbMunger);

		}

		Optional<GeoDistrictEntity> disPurniaO = districtRepo.findByNameAndDivision_Name("Purnia", "Purnia");
		if (disPurniaO.isPresent()) {
			GeoDistrictEntity disPurnia = disPurniaO.get();
			ULBEntity ulbPurnia = ulbRepo.save(getULB("Purnia Nagar Nigam", "ulbPurnia", "ulbPurnia",
					ULBType.MUNICIPAL_CORPORATION, disPurnia, baseHeads, createULBUserGroup("Purnia Nagar Nigam")));
			ulbRepo.save(ulbPurnia);
		}

		Optional<GeoDistrictEntity> disKatiharO = districtRepo.findByNameAndDivision_Name("Katihar", "Purnia");
		if (disKatiharO.isPresent()) {
			GeoDistrictEntity disKatihar = disKatiharO.get();
			ULBEntity ulbKatihar = ulbRepo.save(getULB("Katihar Nagar Nigam", "ulbKatihar", "ulbKatihar",
					ULBType.MUNICIPAL_CORPORATION, disKatihar, baseHeads, createULBUserGroup("Katihar Nagar Nigam")));
			ulbRepo.save(ulbKatihar);

		}

		Optional<GeoDistrictEntity> disNPPatnaO = districtRepo.findByNameAndDivision_Name("Patna", "Patna");
		if (disNPPatnaO.isPresent()) {

			GeoDistrictEntity disNPPatna = disNPPatnaO.get();

			ULBEntity ulbBarhNP = ulbRepo.save(getULB("Nagar Parisad,Barh", "ulbBarhNP", "ulbBarhNP",
					ULBType.MUNICIPAL_COUNCIL, disNPPatna, baseHeads, createULBUserGroup("Nagar Parisad,Barh")));
			ulbRepo.save(ulbBarhNP);

			ULBEntity ulbKhagaulNP = ulbRepo.save(getULB("Nagar Parisad,Khagaul", "ulbKhagaulNP", "ulbKhagaulNP",
					ULBType.MUNICIPAL_COUNCIL, disNPPatna, baseHeads, createULBUserGroup("Nagar Parisad,Khagaul")));
			ulbRepo.save(ulbKhagaulNP);

			ULBEntity ulbDanapurNP = ulbRepo.save(getULB("Nagar Parisad,Danapur", "ulbDanapurNP", "ulbDanapurNP",
					ULBType.MUNICIPAL_COUNCIL, disNPPatna, baseHeads, createULBUserGroup("Nagar Parisad,Danapur")));
			ulbRepo.save(ulbDanapurNP);

			ULBEntity ulbMasaurhiNP = ulbRepo.save(getULB("Nagar Parisad,Masaurhi", "ulbMasaurhiNP", "ulbMasaudiNP",
					ULBType.MUNICIPAL_COUNCIL, disNPPatna, baseHeads, createULBUserGroup("Nagar Parisad,Masaurhi")));
			ulbRepo.save(ulbMasaurhiNP);

			ULBEntity ulbMokamaNP = ulbRepo.save(getULB("Nagar Parisad,Mokama", "ulbMokamaNP", "ulbMokamaNP",
					ULBType.MUNICIPAL_COUNCIL, disNPPatna, baseHeads, createULBUserGroup("Nagar Parisad,Mokama")));
			ulbRepo.save(ulbMokamaNP);

			ULBEntity ulbPhulwariNP = ulbRepo.save(
					getULB("Nagar Parisad,PhulwariSarif", "ulbPhulwariNP", "ulbPhulwariNP", ULBType.MUNICIPAL_COUNCIL,
							disNPPatna, baseHeads, createULBUserGroup("Nagar Parisad,PhulwariSarif")));
			ulbRepo.save(ulbPhulwariNP);

			ULBEntity ulbBakhtiyarpurNP = ulbRepo.save(getULB("Nagar Parisad,Bakhtiyarpur", "ulbBakhtiyarpurNP",
					"ulbBakhtiyarpurNP", ULBType.MUNICIPAL_COUNCIL, disNPPatna, baseHeads,
					createULBUserGroup("Nagar Parisad,Bakhtiyarpur")));
			ulbRepo.save(ulbBakhtiyarpurNP);

			ULBEntity ulbFatuhaNP = ulbRepo.save(getULB("Nagar Parisad,Fatuha", "ulbFatuhaNP", "ulbFatuhaNP",
					ULBType.MUNICIPAL_COUNCIL, disNPPatna, baseHeads, createULBUserGroup("Nagar Parisad,Fatuha")));
			ulbRepo.save(ulbFatuhaNP);

		}

		Optional<GeoDistrictEntity> disNPBuxarO = districtRepo.findByNameAndDivision_Name("Buxar", "Patna");
		if (disNPBuxarO.isPresent()) {

			GeoDistrictEntity disNPBuxar = disNPBuxarO.get();

			ULBEntity ulbBuxarNP = ulbRepo.save(getULB("Nagar Parisad,Buxar", "ulbBuxarNP", "ulbBuxarNP",
					ULBType.MUNICIPAL_COUNCIL, disNPBuxar, baseHeads, createULBUserGroup("Nagar Parisad,Buxar")));
			ulbRepo.save(ulbBuxarNP);

			ULBEntity ulbDumraoNP = ulbRepo.save(getULB("Nagar Parisad,Dumrao", "ulbDumraoNP", "ulbBadhNP",
					ULBType.MUNICIPAL_COUNCIL, disNPBuxar, baseHeads, createULBUserGroup("Nagar Parisad,Dumrao")));
			ulbRepo.save(ulbDumraoNP);
		}

		Optional<GeoDistrictEntity> disNPRohtasO = districtRepo.findByNameAndDivision_Name("Rohtas", "Patna");
		if (disNPRohtasO.isPresent()) {
			GeoDistrictEntity disNPRohtas = disNPRohtasO.get();

			ULBEntity ulbSasaramNP = ulbRepo.save(getULB("Nagar Parisad,Sasaram", "ulbSasaramNP", "ulbSasaramNP",
					ULBType.MUNICIPAL_COUNCIL, disNPRohtas, baseHeads, createULBUserGroup("Nagar Parisad,Sasaram")));
			ulbRepo.save(ulbSasaramNP);

			ULBEntity ulbDalmiaNP = ulbRepo.save(
					getULB("Nagar Parisad,Dehri-Dalmianagar", "ulbDalmiaNP", "ulbDalmiaNP", ULBType.MUNICIPAL_COUNCIL,
							disNPRohtas, baseHeads, createULBUserGroup("Nagar Parisad,Dehri-Dalmianagar")));
			ulbRepo.save(ulbDalmiaNP);

		}

		Optional<GeoDistrictEntity> disNPKaimurO = districtRepo.findByNameAndDivision_Name("Kaimur", "Patna");
		if (disNPKaimurO.isPresent()) {
			GeoDistrictEntity disNPKaimur = disNPKaimurO.get();
			ULBEntity ulbBhabhuaNP = ulbRepo.save(getULB("Nagar Parisad,Bhabhua", "ulbBhabhuaNP", "ulbBhabhuaNP",
					ULBType.MUNICIPAL_COUNCIL, disNPKaimur, baseHeads, createULBUserGroup("Nagar Parisad,Bhabhua")));
			ulbRepo.save(ulbBhabhuaNP);

		}

		Optional<GeoDistrictEntity> disNPNalandaO = districtRepo.findByNameAndDivision_Name("Nalanda", "Patna");
		if (disNPNalandaO.isPresent()) {
			GeoDistrictEntity disNPNalanda = disNPNalandaO.get();
			ULBEntity ulbHilsaNP = ulbRepo.save(getULB("Nagar Parisad,Hilsa", "ulbHilsaNP", "ulbHilsaNP",
					ULBType.MUNICIPAL_COUNCIL, disNPNalanda, baseHeads, createULBUserGroup("Nagar Parisad,Hilsa")));
			ulbRepo.save(ulbHilsaNP);

		}

		// patna division ulbs end here

		// magadh division ulb start here

		Optional<GeoDistrictEntity> disNPJahanabadO = districtRepo.findByNameAndDivision_Name("Jehanabad", "Magadh");
		if (disNPJahanabadO.isPresent()) {
			GeoDistrictEntity disNPJahanabad = disNPJahanabadO.get();
			ULBEntity ulbJehanabadNP = ulbRepo.save(
					getULB("Nagar Parisad,Jehanabad", "ulbJehanabadNP", "ulbJehanabadNP", ULBType.MUNICIPAL_COUNCIL,
							disNPJahanabad, baseHeads, createULBUserGroup("Nagar Parisad,Jehanabad")));
			ulbRepo.save(ulbJehanabadNP);

		}

		Optional<GeoDistrictEntity> disNPArwalO = districtRepo.findByNameAndDivision_Name("Arwal", "Magadh");
		if (disNPArwalO.isPresent()) {
			GeoDistrictEntity disNPArwal = disNPArwalO.get();
			ULBEntity ulbArwalNP = ulbRepo.save(getULB("Nagar Parisad,Arwal", "ulbArwalNP", "ulbArwalNP",
					ULBType.MUNICIPAL_COUNCIL, disNPArwal, baseHeads, createULBUserGroup("Nagar Parisad,Arwal")));
			ulbRepo.save(ulbArwalNP);

		}

		Optional<GeoDistrictEntity> disNPAurangabadO = districtRepo.findByNameAndDivision_Name("Aurangabad", "Magadh");
		if (disNPAurangabadO.isPresent()) {
			GeoDistrictEntity disNPAurangabad = disNPAurangabadO.get();

			ULBEntity ulbAurangabadNP = ulbRepo.save(
					getULB("Nagar Parisad,Aurangabad", "ulbAurangabadNP", "ulbAurangabadNP", ULBType.MUNICIPAL_COUNCIL,
							disNPAurangabad, baseHeads, createULBUserGroup("Nagar Parisad,Aurangabad")));
			ulbRepo.save(ulbAurangabadNP);

		}

		Optional<GeoDistrictEntity> disNPNawadaO = districtRepo.findByNameAndDivision_Name("Nawada", "Magadh");
		if (disNPNawadaO.isPresent()) {
			GeoDistrictEntity disNPNawada = disNPNawadaO.get();
			ULBEntity ulbNawadaNP = ulbRepo.save(getULB("Nagar Parisad,Nawada", "ulbNawadaNP", "ulbNawadaNP",
					ULBType.MUNICIPAL_COUNCIL, disNPNawada, baseHeads, createULBUserGroup("Nagar Parisad,Nawada")));
			ulbRepo.save(ulbNawadaNP);

		}

		// magadh division ulb end here

		// Tirhut division ulb start here

		Optional<GeoDistrictEntity> disNPSitamadhiO = districtRepo.findByNameAndDivision_Name("Sitamarhi", "Tirhut");
		if (disNPSitamadhiO.isPresent()) {
			GeoDistrictEntity disNPSitamadhi = disNPSitamadhiO.get();
			ULBEntity ulbSitamadhiNP = ulbRepo.save(
					getULB("Nagar Parisad,Sitamarhi", "ulbSitamarhiNP", "ulbSitamarhiNP", ULBType.MUNICIPAL_COUNCIL,
							disNPSitamadhi, baseHeads, createULBUserGroup("Nagar Parisad,Sitamarhi")));
			ulbRepo.save(ulbSitamadhiNP);

		}

		Optional<GeoDistrictEntity> disNPVaishaliO = districtRepo.findByNameAndDivision_Name("Vaishali", "Tirhut");
		if (disNPVaishaliO.isPresent()) {
			GeoDistrictEntity disNPVaishali = disNPVaishaliO.get();

			ULBEntity ulbHajipurNP = ulbRepo.save(getULB("Nagar Parisad,Hajipur", "ulbHajipurNP", "ulbHajipurNP",
					ULBType.MUNICIPAL_COUNCIL, disNPVaishali, baseHeads, createULBUserGroup("Nagar Parisad,Hajipur")));
			ulbRepo.save(ulbHajipurNP);

		}

		Optional<GeoDistrictEntity> disNPEastO = districtRepo.findByNameAndDivision_Name("East Champaran", "Tirhut");
		if (disNPEastO.isPresent()) {
			GeoDistrictEntity disNPEast = disNPEastO.get();

			ULBEntity ulbMotihariNP = ulbRepo.save(getULB("Nagar Parisad,Motihari", "ulbMotihariNP", "ulbMotihariNP",
					ULBType.MUNICIPAL_COUNCIL, disNPEast, baseHeads, createULBUserGroup("Nagar Parisad,Motihari")));
			ulbRepo.save(ulbMotihariNP);

			ULBEntity ulbRaksaulNP = ulbRepo.save(getULB("Nagar Parisad,Raksaul", "ulbRaksaulNP", "ulbRaksaulNP",
					ULBType.MUNICIPAL_COUNCIL, disNPEast, baseHeads, createULBUserGroup("Nagar Parisad,Raksaul")));
			ulbRepo.save(ulbRaksaulNP);

		}

		Optional<GeoDistrictEntity> disNPWestO = districtRepo.findByNameAndDivision_Name("West Champaran", "Tirhut");
		if (disNPWestO.isPresent()) {
			GeoDistrictEntity disNPWest = disNPWestO.get();

			ULBEntity ulbBettiahNP = ulbRepo.save(getULB("Nagar Parisad,Bettiah", "ulbBettiahNP", "ulbBettiahNP",
					ULBType.MUNICIPAL_COUNCIL, disNPWest, baseHeads, createULBUserGroup("Nagar Parisad,Bettiah")));
			ulbRepo.save(ulbBettiahNP);

			ULBEntity ulbBaghaNP = ulbRepo.save(getULB("Nagar Parisad,Bagha", "ulbBaghaNP", "ulbBaghaNP",
					ULBType.MUNICIPAL_COUNCIL, disNPWest, baseHeads, createULBUserGroup("Nagar Parisad,Bagha")));
			ulbRepo.save(ulbBaghaNP);

			ULBEntity ulbNarkatiaganjNP = ulbRepo.save(getULB("Nagar Parisad,Narkatiaganj", "ulbNarkatiaganjNP",
					"ulbNarkatiaganjNP", ULBType.MUNICIPAL_COUNCIL, disNPWest, baseHeads,
					createULBUserGroup("Nagar Parisad,Narkatiaganj")));
			ulbRepo.save(ulbNarkatiaganjNP);

		}

		// tirhut divisions ulb end here

		// Darbhanga divisions ulb start here

		Optional<GeoDistrictEntity> disNPDarbhangaO = districtRepo.findByNameAndDivision_Name("Darbhanga", "Darbhanga");
		if (disNPDarbhangaO.isPresent()) {
			GeoDistrictEntity disNPDarbhanga = disNPDarbhangaO.get();
			ULBEntity ulbBenipurNP = ulbRepo.save(getULB("Nagar Parisad,Benipur", "ulbBenipurNP", "ulbBenipurNP",
					ULBType.MUNICIPAL_COUNCIL, disNPDarbhanga, baseHeads, createULBUserGroup("Nagar Parisad,Benipur")));
			ulbRepo.save(ulbBenipurNP);

		}

		Optional<GeoDistrictEntity> disNPMadhubaniO = districtRepo.findByNameAndDivision_Name("Madhubani", "Darbhanga");
		if (disNPMadhubaniO.isPresent()) {
			GeoDistrictEntity disNPMadhubani = disNPMadhubaniO.get();
			ULBEntity ulbMadhubaniNP = ulbRepo.save(
					getULB("Nagar Parisad,Madhubani", "ulbMadhubaniNP", "ulbMadhubaniNP", ULBType.MUNICIPAL_COUNCIL,
							disNPMadhubani, baseHeads, createULBUserGroup("Nagar Parisad,Madhubani")));
			ulbRepo.save(ulbMadhubaniNP);

		}

		Optional<GeoDistrictEntity> disNPSamastipurO = districtRepo.findByNameAndDivision_Name("Samastipur",
				"Darbhanga");
		if (disNPSamastipurO.isPresent()) {
			GeoDistrictEntity disNPSamastipur = disNPSamastipurO.get();
			ULBEntity ulbSamastipurNP = ulbRepo.save(
					getULB("Nagar Parisad,Samastipur", "ulbSamastipurNP", "ulbSamastipurNP", ULBType.MUNICIPAL_COUNCIL,
							disNPSamastipur, baseHeads, createULBUserGroup("Nagar Parisad,Samastipur")));
			ulbRepo.save(ulbSamastipurNP);

		}

		// Darbhanga divisions end

		// bhagalpur divisions start

		Optional<GeoDistrictEntity> disNPBhagalpurO = districtRepo.findByNameAndDivision_Name("Bhagalpur", "Bhagalpur");
		if (disNPBhagalpurO.isPresent()) {
			GeoDistrictEntity disNPBhagalpur = disNPBhagalpurO.get();

			ULBEntity ulbSultanganjNP = ulbRepo.save(
					getULB("Nagar Parisad,Sultanganj", "ulbSultanganjNP", "ulbSultanganjNP", ULBType.MUNICIPAL_COUNCIL,
							disNPBhagalpur, baseHeads, createULBUserGroup("Nagar Parisad,Sultanganj")));
			ulbRepo.save(ulbSultanganjNP);

		}
		// bhagalpur divisions end

		// Munger divisions start

		Optional<GeoDistrictEntity> disNPJamalpurO = districtRepo.findByNameAndDivision_Name("Munger", "Munger");
		if (disNPJamalpurO.isPresent()) {
			GeoDistrictEntity disNPJamalpur = disNPJamalpurO.get();
			ULBEntity ulbJamalpurNP = ulbRepo.save(getULB("Nagar Parisad,Jamalpur", "ulbJamalpurNP", "ulbJamalpurNP",
					ULBType.MUNICIPAL_COUNCIL, disNPJamalpur, baseHeads, createULBUserGroup("Nagar Parisad,Jamalpur")));
			ulbRepo.save(ulbJamalpurNP);

		}

		Optional<GeoDistrictEntity> disNPLakhisaraiO = districtRepo.findByNameAndDivision_Name("Lakhisarai", "Munger");
		if (disNPLakhisaraiO.isPresent()) {
			GeoDistrictEntity disNPLakhisarai = disNPLakhisaraiO.get();
			ULBEntity ulbLakhisaraiNP = ulbRepo.save(
					getULB("Nagar Parisad,Lakhisarai", "ulbLakhisaraiNP", "ulbLakhisaraiNP", ULBType.MUNICIPAL_COUNCIL,
							disNPLakhisarai, baseHeads, createULBUserGroup("Nagar Parisad,Lakhisarai")));
			ulbRepo.save(ulbLakhisaraiNP);

		}

		Optional<GeoDistrictEntity> disNPSheikhpuraO = districtRepo.findByNameAndDivision_Name("Sheikhpura", "Munger");
		if (disNPSheikhpuraO.isPresent()) {
			GeoDistrictEntity disNPSheikhpura = disNPSheikhpuraO.get();

			ULBEntity ulbSheikhpuraNP = ulbRepo.save(
					getULB("Nagar Parisad,Sheikhpura", "ulbSheikhpuraNP", "ulbSheikhpuraNP", ULBType.MUNICIPAL_COUNCIL,
							disNPSheikhpura, baseHeads, createULBUserGroup("Nagar Parisad,Sheikhpura")));
			ulbRepo.save(ulbSheikhpuraNP);

		}

		Optional<GeoDistrictEntity> disNPJamuiO = districtRepo.findByNameAndDivision_Name("Jamui", "Munger");
		if (disNPJamuiO.isPresent()) {
			GeoDistrictEntity disNPJamui = disNPJamuiO.get();
			ULBEntity ulbJamuiNP = ulbRepo.save(getULB("Nagar Parisad,Jamui", "ulbJamuiNP", "ulbJamuiNP",
					ULBType.MUNICIPAL_COUNCIL, disNPJamui, baseHeads, createULBUserGroup("Nagar Parisad,Jamui")));
			ulbRepo.save(ulbJamuiNP);

		}

		Optional<GeoDistrictEntity> disNPKhagariaO = districtRepo.findByNameAndDivision_Name("Khagaria", "Munger");
		if (disNPKhagariaO.isPresent()) {
			GeoDistrictEntity disNPKhagaria = disNPKhagariaO.get();
			ULBEntity ulbKhagariaNP = ulbRepo.save(getULB("Nagar Parisad,Khagaria", "ulbKhagariaNP", "ulbKhagariaNP",
					ULBType.MUNICIPAL_COUNCIL, disNPKhagaria, baseHeads, createULBUserGroup("Nagar Parisad,Khagaria")));
			ulbRepo.save(ulbKhagariaNP);

		}
		Optional<GeoDistrictEntity> disNPBegusaraiO = districtRepo.findByNameAndDivision_Name("Begusarai", "Munger");
		if (disNPBegusaraiO.isPresent()) {
			GeoDistrictEntity disNPBegusarai = disNPBegusaraiO.get();
			ULBEntity ulbBegusaraiNP = ulbRepo.save(getULB("Nagar Parisad,Bihat", "ulbBihatNP", "ulbBihatNP",
					ULBType.MUNICIPAL_COUNCIL, disNPBegusarai, baseHeads, createULBUserGroup("Nagar Parisad,Bihat")));
			ulbRepo.save(ulbBegusaraiNP);

		}

		// munger division ulbs end here

		// Saran division ulbs start here
		Optional<GeoDistrictEntity> disNPSaranO = districtRepo.findByNameAndDivision_Name("Saran", "Saran");
		if (disNPSaranO.isPresent()) {
			GeoDistrictEntity disNPSaran = disNPSaranO.get();

			ULBEntity ulbChhapraNP = ulbRepo.save(getULB("Nagar Parisad,Chhapra", "ulbChhapraNP", "ulbChhapraNP",
					ULBType.MUNICIPAL_COUNCIL, disNPSaran, baseHeads, createULBUserGroup("Nagar Parisad,Chhapra")));
			ulbRepo.save(ulbChhapraNP);

		}

		Optional<GeoDistrictEntity> disNPSiwanO = districtRepo.findByNameAndDivision_Name("Siwan", "Saran");
		if (disNPSiwanO.isPresent()) {
			GeoDistrictEntity disNPSiwan = disNPSiwanO.get();

			ULBEntity ulbSiwanNP = ulbRepo.save(getULB("Nagar Parisad,Siwan", "ulbSiwanNP", "ulbSiwanNP",
					ULBType.MUNICIPAL_COUNCIL, disNPSiwan, baseHeads, createULBUserGroup("Nagar Parisad,Siwan")));
			ulbRepo.save(ulbSiwanNP);

		}

		Optional<GeoDistrictEntity> disNPGopalganjO = districtRepo.findByNameAndDivision_Name("Gopalganj", "Saran");
		if (disNPGopalganjO.isPresent()) {
			GeoDistrictEntity disNPGopalganj = disNPGopalganjO.get();
			ULBEntity ulbGopalganjNP = ulbRepo.save(
					getULB("Nagar Parisad,Gopalganj", "ulbGopalganjNP", "ulbGopalganjNP", ULBType.MUNICIPAL_COUNCIL,
							disNPGopalganj, baseHeads, createULBUserGroup("Nagar Parisad,Gopalganj")));
			ulbRepo.save(ulbGopalganjNP);

		}

		// saran divisions end

		// koshi division start

		Optional<GeoDistrictEntity> disNPSaharsaO = districtRepo.findByNameAndDivision_Name("Saharsa", "Koshi");
		if (disNPSaharsaO.isPresent()) {
			GeoDistrictEntity disNPSaharsa = disNPSaharsaO.get();
			ULBEntity ulbSaharsaNP = ulbRepo.save(getULB("Nagar Parisad,Saharsa", "ulbSaharsaNP", "ulbSaharsaNP",
					ULBType.MUNICIPAL_COUNCIL, disNPSaharsa, baseHeads, createULBUserGroup("Nagar Parisad,Saharsa")));
			ulbRepo.save(ulbSaharsaNP);

		}

		Optional<GeoDistrictEntity> disNPMadhepuraO = districtRepo.findByNameAndDivision_Name("Madhepura", "Koshi");
		if (disNPMadhepuraO.isPresent()) {
			GeoDistrictEntity disNPMadhepura = disNPMadhepuraO.get();
			ULBEntity ulbMadhepuraNP = ulbRepo.save(
					getULB("Nagar Parisad,Madhepura", "ulbMadhepuraNP", "ulbMadhepuraNP", ULBType.MUNICIPAL_COUNCIL,
							disNPMadhepura, baseHeads, createULBUserGroup("Nagar Parisad,Madhepura")));
			ulbRepo.save(ulbMadhepuraNP);

		}

		Optional<GeoDistrictEntity> disNPSupaulO = districtRepo.findByNameAndDivision_Name("Supaul", "Koshi");
		if (disNPSupaulO.isPresent()) {
			GeoDistrictEntity disNPSupaul = disNPSupaulO.get();

			ULBEntity ulbSupaulNP = ulbRepo.save(getULB("Nagar Parisad,Supaul", "ulbSupaulNP", "ulbSupaulNP",
					ULBType.MUNICIPAL_COUNCIL, disNPSupaul, baseHeads, createULBUserGroup("Nagar Parisad,Supaul")));
			ulbRepo.save(ulbSupaulNP);

		}

		// koshi division ulbs end here

		// purnia divisions

		Optional<GeoDistrictEntity> disNPArariaO = districtRepo.findByNameAndDivision_Name("Araria", "Purnia");
		if (disNPArariaO.isPresent()) {
			GeoDistrictEntity disNPAraria = disNPArariaO.get();

			ULBEntity ulbArariaNP = ulbRepo.save(getULB("Nagar Parisad,Araria", "ulbArariaNP", "ulbArariaNP",
					ULBType.MUNICIPAL_COUNCIL, disNPAraria, baseHeads, createULBUserGroup("Nagar Parisad,Araria")));
			ulbRepo.save(ulbArariaNP);

			ULBEntity ulbForbesganjNP = ulbRepo.save(
					getULB("Nagar Parisad,Forbesganj", "ulbForbesganjNP", "ulbForbesganjNP", ULBType.MUNICIPAL_COUNCIL,
							disNPAraria, baseHeads, createULBUserGroup("Nagar Parisad,Forbesganj")));
			ulbRepo.save(ulbForbesganjNP);

		}

		Optional<GeoDistrictEntity> disNPKishanganjO = districtRepo.findByNameAndDivision_Name("Kishanganj", "Purnia");
		if (disNPKishanganjO.isPresent()) {
			GeoDistrictEntity disNPKishanganj = disNPKishanganjO.get();

			ULBEntity ulbKishanganjNP = ulbRepo.save(
					getULB("Nagar Parisad,Kishanganj", "ulbKishanganjNP", "ulbKishanganjNP", ULBType.MUNICIPAL_COUNCIL,
							disNPKishanganj, baseHeads, createULBUserGroup("Nagar Parisad,Kishanganj")));
			ulbRepo.save(ulbKishanganjNP);

		}
		// purnia divisions end heres

		// municipal council type ulbs complete

		// Nagar Panchayat Ulbs type start

		Optional<GeoDistrictEntity> disNPtPatnaO = districtRepo.findByNameAndDivision_Name("Patna", "Patna");
		if (disNPtPatnaO.isPresent()) {
			GeoDistrictEntity disNPtPatna = disNPtPatnaO.get();

			saveAllTypeUlbs(getULB("Nagar Panchayat,Maner", "ulbKishanganjNP", "ulbKishanganjNP",
					ULBType.NAGAR_PANCHAYAT, disNPtPatna, baseHeads, createULBUserGroup("Nagar Panchayat,Maner")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Khushrupur", "ulbKhushrupurNPt", "ulbKhushrupurNPt",
					ULBType.NAGAR_PANCHAYAT, disNPtPatna, baseHeads, createULBUserGroup("Nagar Panchayat,Khushrupur")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Bikram", "ulbBikramNPt", "ulbBikramNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtPatna, baseHeads, createULBUserGroup("Nagar Panchayat,Bikram")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Naubatpur", "ulbNaubatpurNPt", "ulbNaubatpurNPt",
					ULBType.NAGAR_PANCHAYAT, disNPtPatna, baseHeads, createULBUserGroup("Nagar Panchayat,Naubatpur")));

		}

		// patna district nagar panchayat end

		Optional<GeoDistrictEntity> disNPtBhojpurO = districtRepo.findByNameAndDivision_Name("Bhojpur", "Patna");
		if (disNPtBhojpurO.isPresent()) {
			GeoDistrictEntity disNPtBhojpur = disNPtBhojpurO.get();

			saveAllTypeUlbs(getULB("Nagar Panchayat,Piro", "ulbPiroNPt", "ulbPiroNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtBhojpur, baseHeads, createULBUserGroup("Nagar Panchayat,Piro")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Bihiyan", "ulbBihiyanNPt", "ulbBihiyanNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtBhojpur, baseHeads, createULBUserGroup("Nagar Panchayat,Bihiyan")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Jagdishpur", "ulbJagdishpurNPt", "ulbJagdishpurNPt",
					ULBType.NAGAR_PANCHAYAT, disNPtBhojpur, baseHeads,
					createULBUserGroup("Nagar Panchayat,Jagdishpur")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Koilwar", "ulbKoilwarNPt", "ulbKoilwarNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtBhojpur, baseHeads, createULBUserGroup("Nagar Panchayat,Koilwar")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Shahpur", "ulbShahpurNPt", "ulbShahpurNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtBhojpur, baseHeads, createULBUserGroup("Nagar Panchayat,Shahpur")));

		}

		Optional<GeoDistrictEntity> disNPtRohtasO = districtRepo.findByNameAndDivision_Name("Rohtas", "Patna");
		if (disNPtRohtasO.isPresent()) {
			GeoDistrictEntity disNPtRohtas = disNPtRohtasO.get();

			saveAllTypeUlbs(getULB("Nagar Panchayat,Koath", "ulbKoathNPt", "ulbKoathNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtRohtas, baseHeads, createULBUserGroup("Nagar Panchayat,Koath")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Nokha", "ulbNokhaNPt", "ulbNokhaNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtRohtas, baseHeads, createULBUserGroup("Nagar Panchayat,Nokha")));

			saveAllTypeUlbs(
					getULB("Nagar Panchayat,Bikramganj", "ulBikramganjNPt", "ulbBikramganjNPt", ULBType.NAGAR_PANCHAYAT,
							disNPtRohtas, baseHeads, createULBUserGroup("Nagar Panchayat,Bikramganj")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Nasriganj", "ulbNasriganjNPt", "ulbNasriganjNPt",
					ULBType.NAGAR_PANCHAYAT, disNPtRohtas, baseHeads, createULBUserGroup("Nagar Panchayat,Nasriganj")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Kochas", "ulbKochasNPt", "ulbKochasNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtRohtas, baseHeads, createULBUserGroup("Nagar Panchayat,Kochas")));

		}

		Optional<GeoDistrictEntity> disNPtMohaniaO = districtRepo.findByNameAndDivision_Name("Kaimur", "Patna");
		if (disNPtMohaniaO.isPresent()) {
			GeoDistrictEntity disNPtMohania = disNPtMohaniaO.get();
			saveAllTypeUlbs(getULB("Nagar Panchayat,Mohania", "ulbMohaniaNPt", "ulbMohaniaNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtMohania, baseHeads, createULBUserGroup("Nagar Panchayat,Mohania")));

		}

		Optional<GeoDistrictEntity> disNPtNalandaO = districtRepo.findByNameAndDivision_Name("Nalanda", "Patna");
		if (disNPtNalandaO.isPresent()) {
			GeoDistrictEntity disNPtNalanda = disNPtNalandaO.get();

			saveAllTypeUlbs(getULB("Nagar Panchayat,Islampur", "ulbIslampurNPt", "ulbIslampurNPt",
					ULBType.NAGAR_PANCHAYAT, disNPtNalanda, baseHeads, createULBUserGroup("Nagar Panchayat,Islampur")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Silao", "ulbSilaoNPt", "ulbSilaoNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtNalanda, baseHeads, createULBUserGroup("Nagar Panchayat,Silao")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Rajgir", "ulbRajgirNPt", "ulbRajgirNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtNalanda, baseHeads, createULBUserGroup("Nagar Panchayat,Rajgir")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Harnaut", "ulbHarnautNPt", "ulbHarnautNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtNalanda, baseHeads, createULBUserGroup("Nagar Panchayat,Harnaut")));

		}

		// NAGAR_PANCHAYAT patna division end here

		Optional<GeoDistrictEntity> disNPtGayaO = districtRepo.findByNameAndDivision_Name("Gaya", "Magadh");
		if (disNPtGayaO.isPresent()) {
			GeoDistrictEntity disNPtGaya = disNPtGayaO.get();

			saveAllTypeUlbs(getULB("Nagar Panchayat,Bodhgaya", "ulbBodhgayaNPt", "ulbBodhgayaNPt",
					ULBType.NAGAR_PANCHAYAT, disNPtGaya, baseHeads, createULBUserGroup("Nagar Panchayat,Bodhgaya")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Sherghati", "ulbSherghatiNPt", "ulbSherghatiNPt",
					ULBType.NAGAR_PANCHAYAT, disNPtGaya, baseHeads, createULBUserGroup("Nagar Panchayat,Sherghati")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Tekari", "ulbTekariNPt", "ulbTekariNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtGaya, baseHeads, createULBUserGroup("Nagar Panchayat,Tekari")));

		}

		Optional<GeoDistrictEntity> disNPtJahanabadO = districtRepo.findByNameAndDivision_Name("Jahanabad", "Magadh");
		if (disNPtJahanabadO.isPresent()) {
			GeoDistrictEntity disNPtJahanabad = disNPtJahanabadO.get();
			saveAllTypeUlbs(getULB("Nagar Panchayat,Makhdumpur", "ulbMakhdumpurNPt", "ulbMakhdumpurNPt",
					ULBType.NAGAR_PANCHAYAT, disNPtJahanabad, baseHeads,
					createULBUserGroup("Nagar Panchayat,Makhdumpur")));

		}

		Optional<GeoDistrictEntity> disNPtAurangabadO = districtRepo.findByNameAndDivision_Name("Aurangabad", "Magadh");
		if (disNPtAurangabadO.isPresent()) {
			GeoDistrictEntity disNPtAurangabad = disNPtAurangabadO.get();
			saveAllTypeUlbs(
					getULB("Nagar Panchayat,Rafiganj", "ulbRafiganjNPt", "ulbRafiganjNPt", ULBType.NAGAR_PANCHAYAT,
							disNPtAurangabad, baseHeads, createULBUserGroup("Nagar Panchayat,Rafiganj")));

			saveAllTypeUlbs(
					getULB("Nagar Panchayat,Navinagar", "ulbNavinagarNPt", "ulbNavinagarNPt", ULBType.NAGAR_PANCHAYAT,
							disNPtAurangabad, baseHeads, createULBUserGroup("Nagar Panchayat,Navinagar")));

			saveAllTypeUlbs(
					getULB("Nagar Panchayat,Daudnagar", "ulbDaudnagarNPt", "ulbDaudnagarNPt", ULBType.NAGAR_PANCHAYAT,
							disNPtAurangabad, baseHeads, createULBUserGroup("Nagar Panchayat,Daudnagar")));

		}

		Optional<GeoDistrictEntity> disNPtNawadaO = districtRepo.findByNameAndDivision_Name("Nawada", "Magadh");
		if (disNPtNawadaO.isPresent()) {
			GeoDistrictEntity disNPtNawada = disNPtNawadaO.get();
			saveAllTypeUlbs(getULB("Nagar Panchayat,Warsaliganj", "ulbWganjNPt", "ulbWganjNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtNawada, baseHeads, createULBUserGroup("Nagar Panchayat,Warsaliganj")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Hisua", "ulbHisuaNPt", "ulbHisuaNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtNawada, baseHeads, createULBUserGroup("Nagar Panchayat,Hisua")));

		}

		// NAGAR_PANCHAYAT Magadh division end here

		Optional<GeoDistrictEntity> disNPtBhagalpurO = districtRepo.findByNameAndDivision_Name("Bhagalpur",
				"Bhagalpur");
		if (disNPtBhagalpurO.isPresent()) {
			GeoDistrictEntity disNPtBhagalpur = disNPtBhagalpurO.get();
			saveAllTypeUlbs(
					getULB("Nagar Panchayat,Naugachia", "ulbNaugachiaNPt", "ulNaugachiaNPt", ULBType.NAGAR_PANCHAYAT,
							disNPtBhagalpur, baseHeads, createULBUserGroup("Nagar Panchayat,Naugachia")));

			saveAllTypeUlbs(
					getULB("Nagar Panchayat,Kahalgaon", "ulbKahalgaonNPt", "ulbKahalgaonNPt", ULBType.NAGAR_PANCHAYAT,
							disNPtBhagalpur, baseHeads, createULBUserGroup("Nagar Panchayat,Kahalgaon")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Amarpur", "ulbAmarpurNPt", "ulbAmarpurNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtBhagalpur, baseHeads, createULBUserGroup("Nagar Panchayat,Amarpur")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Banka", "ulbBankaNPt", "ulbBankaNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtBhagalpur, baseHeads, createULBUserGroup("Nagar Panchayat,Banka")));

		}

		// NAGAR_PANCHAYAT Bhagalpur division end here

		Optional<GeoDistrictEntity> disNPtSitamarhiO = districtRepo.findByNameAndDivision_Name("Sitamarhi", "Tirhut");
		if (disNPtSitamarhiO.isPresent()) {
			GeoDistrictEntity disNPtSitamarhi = disNPtSitamarhiO.get();
			saveAllTypeUlbs(
					getULB("Nagar Panchayat,Bairgania", "ulbBairganiaNPt", "ulbBairganiaNPt", ULBType.NAGAR_PANCHAYAT,
							disNPtSitamarhi, baseHeads, createULBUserGroup("Nagar Panchayat,Bairgania")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Belsand", "ulbBelsandNPt", "ulbBelsandNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtSitamarhi, baseHeads, createULBUserGroup("Nagar Panchayat,Belsand")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Dumra", "ulbDumraNPt", "ulbDumraNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtSitamarhi, baseHeads, createULBUserGroup("Nagar Panchayat,Dumra")));

			saveAllTypeUlbs(
					getULB("Nagar Panchayat,Janakpur Road", "ulbJanakpurNPt", "ulbJanakpurNPt", ULBType.NAGAR_PANCHAYAT,
							disNPtSitamarhi, baseHeads, createULBUserGroup("Nagar Panchayat,Janakpur Road")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Sursand", "ulbSursandNPt", "ulbSursandNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtSitamarhi, baseHeads, createULBUserGroup("Nagar Panchayat,Sursand")));

		}

		Optional<GeoDistrictEntity> disNPtMuzaffarpurO = districtRepo.findByNameAndDivision_Name("Muzaffarpur",
				"Tirhut");
		if (disNPtMuzaffarpurO.isPresent()) {
			GeoDistrictEntity disNPtMuzaffarpur = disNPtMuzaffarpurO.get();

			saveAllTypeUlbs(getULB("Nagar Panchayat,Motipur", "ulbMotipurNPt", "ulbMotipurNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtMuzaffarpur, baseHeads, createULBUserGroup("Nagar Panchayat,Motipur")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Kati", "ulbKantiNPt", "ulbKantiNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtMuzaffarpur, baseHeads, createULBUserGroup("Nagar Panchayat,Kati")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Shahebganj", "ulbShahebganjNPt", "ulbShahebganjNPt",
					ULBType.NAGAR_PANCHAYAT, disNPtMuzaffarpur, baseHeads,
					createULBUserGroup("Nagar Panchayat,Shahebganj")));

		}

		Optional<GeoDistrictEntity> disNPtVaishaliO = districtRepo.findByNameAndDivision_Name("Vaishali", "Tirhut");
		if (disNPtVaishaliO.isPresent()) {
			GeoDistrictEntity disNPtVaishali = disNPtVaishaliO.get();

			saveAllTypeUlbs(getULB("Nagar Panchayat,Lalganj", "ulbLalganjNPt", "ulLalganjNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtVaishali, baseHeads, createULBUserGroup("Nagar Panchayat,Lalganj")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Mahua", "ulbMahuaNPt", "ulbMahuaNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtVaishali, baseHeads, createULBUserGroup("Nagar Panchayat,Mahua")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Mahnar", "ulbMahnarNPt", "ulbMahnarNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtVaishali, baseHeads, createULBUserGroup("Nagar Panchayat,Mahnar")));

		}

		Optional<GeoDistrictEntity> disNPtShivharO = districtRepo.findByNameAndDivision_Name("Sheohar", "Tirhut");
		if (disNPtShivharO.isPresent()) {
			GeoDistrictEntity disNPtShivhar = disNPtShivharO.get();

			saveAllTypeUlbs(getULB("Nagar Panchayat,Sheohar", "ulbSheoharNPt", "ulbSheoharNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtShivhar, baseHeads, createULBUserGroup("Nagar Panchayat,Sheohar")));
		}

		Optional<GeoDistrictEntity> disNPtEastO = districtRepo.findByNameAndDivision_Name("East Champaran", "Tirhut");
		if (disNPtEastO.isPresent()) {
			GeoDistrictEntity disNPtEast = disNPtEastO.get();

			saveAllTypeUlbs(getULB("Nagar Panchayat,Chakiya", "ulbChakiyaNPt", "ulbChakiyaNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtEast, baseHeads, createULBUserGroup("Nagar Panchayat,Chakiya")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Sugauli", "ulbSugauliNPt", "ulbSugauliNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtEast, baseHeads, createULBUserGroup("Nagar Panchayat,Sugauli")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Areraj", "ulbArerajNPt", "ulbArerajNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtEast, baseHeads, createULBUserGroup("Nagar Panchayat,Areraj")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Kesariya", "ulbKesariaNPt", "ulbKesariaNPt",
					ULBType.NAGAR_PANCHAYAT, disNPtEast, baseHeads, createULBUserGroup("Nagar Panchayat,Kesariya")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Pakaridayal", "ulbPakaridayalNPt", "ulbPakaridayalNPt",
					ULBType.NAGAR_PANCHAYAT, disNPtEast, baseHeads, createULBUserGroup("Nagar Panchayat,Pakaridayal")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Mehsi", "ulbMehsiNPt", "ulbMehsilNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtEast, baseHeads, createULBUserGroup("Nagar Panchayat,Mehsi")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Dhaka", "ulbDhakaNPt", "ulbDhakaNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtEast, baseHeads, createULBUserGroup("Nagar Panchayat,Dhaka")));

		}

		Optional<GeoDistrictEntity> disNPtWestO = districtRepo.findByNameAndDivision_Name("West Champaran", "Tirhut");
		if (disNPtWestO.isPresent()) {
			GeoDistrictEntity disNPtWest = disNPtWestO.get();

			saveAllTypeUlbs(getULB("Nagar Panchayat,Chanpatia", "ulbChanpatiaNPt", "ulbChanpatiaNPt",
					ULBType.NAGAR_PANCHAYAT, disNPtWest, baseHeads, createULBUserGroup("Nagar Panchayat,Chanpatia")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Ramnagar", "ulbRamnagarNPt", "ulRamnagarNPt",
					ULBType.NAGAR_PANCHAYAT, disNPtWest, baseHeads, createULBUserGroup("Nagar Panchayat,Ramnagar")));

		}

		// NAGAR_PANCHAYAT tirhut division end here

		Optional<GeoDistrictEntity> disNPtMadhubaniO = districtRepo.findByNameAndDivision_Name("Madhubani",
				"Darbhanga");
		if (disNPtMadhubaniO.isPresent()) {
			GeoDistrictEntity disNPtMadhubani = disNPtMadhubaniO.get();

			saveAllTypeUlbs(
					getULB("Nagar Panchayat,Jainagar", "ulbJainagarNPt", "ulbJainagarNPt", ULBType.NAGAR_PANCHAYAT,
							disNPtMadhubani, baseHeads, createULBUserGroup("Nagar Panchayat,Jainagar")));
			saveAllTypeUlbs(getULB("Nagar Panchayat,Jhanjharpur", "ulbJhanjharpurNPt", "ulbJhanjharpurNPt",
					ULBType.NAGAR_PANCHAYAT, disNPtMadhubani, baseHeads,
					createULBUserGroup("Nagar Panchayat,Jhanjharpur")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Ghoghardiha", "ulbGhoghardihaNPt", "ulbGhoghardihaNPt",
					ULBType.NAGAR_PANCHAYAT, disNPtMadhubani, baseHeads,
					createULBUserGroup("Nagar Panchayat,Ghoghardiha")));

		}

		Optional<GeoDistrictEntity> disNPtSamastipurO = districtRepo.findByNameAndDivision_Name("Samastipur",
				"Darbhanga");
		if (disNPtSamastipurO.isPresent()) {
			GeoDistrictEntity disNPtSamastipur = disNPtSamastipurO.get();

			saveAllTypeUlbs(getULB("Nagar Panchayat,Dalsinghsarai", "ulbDalsinghsaraihNPt", "ulbDalsinghsaraiNPt",
					ULBType.NAGAR_PANCHAYAT, disNPtSamastipur, baseHeads,
					createULBUserGroup("Nagar Panchayat,Dalsinghsarai")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Rosera", "ulbRoseraNPt", "ulbRoseraNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtSamastipur, baseHeads, createULBUserGroup("Nagar Panchayat,Rosera")));

		}

		// NAGAR_PANCHAYAT Darbhanga division end here

		Optional<GeoDistrictEntity> disNPtMungerO = districtRepo.findByNameAndDivision_Name("Munger", "Munger");
		if (disNPtMungerO.isPresent()) {
			GeoDistrictEntity disNPtMunger = disNPtMungerO.get();

			saveAllTypeUlbs(getULB("Nagar Panchayat,Haveli Kharagpur", "ulbKharagpurNPt", "ulbKharagpurNPt",
					ULBType.NAGAR_PANCHAYAT, disNPtMunger, baseHeads,
					createULBUserGroup("Nagar Panchayat,Haveli Kharagpur")));

		}

		Optional<GeoDistrictEntity> disNPtLakhisaraiO = districtRepo.findByNameAndDivision_Name("Lakhisarai", "Munger");
		if (disNPtLakhisaraiO.isPresent()) {
			GeoDistrictEntity disNPtLakhisarai = disNPtLakhisaraiO.get();

			saveAllTypeUlbs(
					getULB("Barahiya Nagar Panchayat", "ulbBarahiyaNPt", "ulbBarahiyaNPt", ULBType.NAGAR_PANCHAYAT,
							disNPtLakhisarai, baseHeads, createULBUserGroup("Barahiya Nagar Panchayat")));

		}

		Optional<GeoDistrictEntity> disNPtJamuiO = districtRepo.findByNameAndDivision_Name("Jamui", "Munger");
		if (disNPtJamuiO.isPresent()) {
			GeoDistrictEntity disNPtJamui = disNPtJamuiO.get();

			saveAllTypeUlbs(getULB("Nagar Panchayat Jhajha", "ulbJhajhaNPt", "ulbJhajhaNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtJamui, baseHeads, createULBUserGroup("Nagar Panchayat Jhajha")));

		}

		Optional<GeoDistrictEntity> disNPtSheikhpuraO = districtRepo.findByNameAndDivision_Name("Sheikhpura", "Munger");
		if (disNPtSheikhpuraO.isPresent()) {
			GeoDistrictEntity disNPtSheikhpura = disNPtSheikhpuraO.get();

			saveAllTypeUlbs(
					getULB("Nagar Panchayat,Barbigha", "ulbBarbighaNPt", "ulbBarbighaNPt", ULBType.NAGAR_PANCHAYAT,
							disNPtSheikhpura, baseHeads, createULBUserGroup("Nagar Panchayat,Barbigha")));

		}

		Optional<GeoDistrictEntity> disNPtKhagariaO = districtRepo.findByNameAndDivision_Name("Khagaria", "Munger");
		if (disNPtKhagariaO.isPresent()) {
			GeoDistrictEntity disNPtKhagaria = disNPtKhagariaO.get();

			saveAllTypeUlbs(
					getULB("Nagar Panchayat,Gogri Jamalpur", "ulbGogriNPt", "ulbGogriNPt", ULBType.NAGAR_PANCHAYAT,
							disNPtKhagaria, baseHeads, createULBUserGroup("Nagar Panchayat,Gogri Jamalpur")));

		}

		Optional<GeoDistrictEntity> disNPtBegusaraiO = districtRepo.findByNameAndDivision_Name("Begusarai", "Munger");
		if (disNPtBegusaraiO.isPresent()) {
			GeoDistrictEntity disNPtBegusarai = disNPtBegusaraiO.get();

			saveAllTypeUlbs(getULB("Nagar Panchayat,Bakhri", "ulbBakhriNPt", "ulbBakhriNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtBegusarai, baseHeads, createULBUserGroup("Nagar Panchayat,Bakhri")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Teghra", "ulbTeghraNPt", "ulbTeghraNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtBegusarai, baseHeads, createULBUserGroup("Nagar Panchayat,Teghra")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Baliya", "ulbBaliaNPt", "ulbBaliaNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtBegusarai, baseHeads, createULBUserGroup("Nagar Panchayat,Baliya")));

		}

		// NAGAR_PANCHAYAT Munger division end here

		Optional<GeoDistrictEntity> disNPtSaranO = districtRepo.findByNameAndDivision_Name("Saran", "Saran");
		if (disNPtSaranO.isPresent()) {
			GeoDistrictEntity disNPtSaran = disNPtSaranO.get();

			saveAllTypeUlbs(getULB("Nagar Panchayat,Sonpur", "ulbSonpurNPt", "ulbSonpurNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtSaran, baseHeads, createULBUserGroup("Nagar Panchayat,Sonpur")));
			saveAllTypeUlbs(getULB("Nagar Panchayat,Dighwara", "ulbDighwaraNPt", "ulbDighwaraNPt",
					ULBType.NAGAR_PANCHAYAT, disNPtSaran, baseHeads, createULBUserGroup("Nagar Panchayat,Dighwara")));
			saveAllTypeUlbs(getULB("Nagar Panchayat,Marhaura", "ulbMarhauraNPt", "ulbMarhauraNPt",
					ULBType.NAGAR_PANCHAYAT, disNPtSaran, baseHeads, createULBUserGroup("Nagar Panchayat,Marhaura")));
			saveAllTypeUlbs(getULB("Nagar Panchayat,Rivilganj", "ulbRivilganjNPt", "ulRivilganjNPt",
					ULBType.NAGAR_PANCHAYAT, disNPtSaran, baseHeads, createULBUserGroup("Nagar Panchayat,Rivilganj")));
			saveAllTypeUlbs(getULB("Nagar Panchayat,Ekma Bazar", "ulbEkmaNPt", "ulbEkmaNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtSaran, baseHeads, createULBUserGroup("Nagar Panchayat,Ekma Bazar")));
			saveAllTypeUlbs(getULB("Nagar Panchayat,Parsa Bazar", "ulbParsaNPt", "ulbParsaNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtSaran, baseHeads, createULBUserGroup("Nagar Panchayat,Parsa Bazar")));

		}

		Optional<GeoDistrictEntity> disNPtSiwanO = districtRepo.findByNameAndDivision_Name("Siwan", "Saran");
		if (disNPtSiwanO.isPresent()) {
			GeoDistrictEntity disNPtSiwan = disNPtSiwanO.get();
			saveAllTypeUlbs(getULB("Nagar Panchayat,Maharajganj", "ulbMaharajganjNPt", "ulbMaharajganjNPt",
					ULBType.NAGAR_PANCHAYAT, disNPtSiwan, baseHeads,
					createULBUserGroup("Nagar Panchayat,Maharajganj")));

			saveAllTypeUlbs(getULB("Nagar Panchayat,Mairwan", "ulbMairwanNPt", "ulbMairwanNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtSiwan, baseHeads, createULBUserGroup("Nagar Panchayat,Mairwan")));

		}

		Optional<GeoDistrictEntity> disNPtGopalganjO = districtRepo.findByNameAndDivision_Name("Gopalganj", "Saran");
		if (disNPtGopalganjO.isPresent()) {
			GeoDistrictEntity disNPtGopalganj = disNPtGopalganjO.get();
			saveAllTypeUlbs(getULB("Nagar Panchayat,Mirganj", "ulbMirganjNPt", "ulbMirganjNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtGopalganj, baseHeads, createULBUserGroup("Nagar Panchayat,Mirganj")));
			saveAllTypeUlbs(getULB("Nagar Panchayat,Barauli", "ulbBarauliNPt", "ulbBarauliNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtGopalganj, baseHeads, createULBUserGroup("Nagar Panchayat,Barauli")));
			saveAllTypeUlbs(getULB("Nagar Panchayat,Kateya", "ulbKateyaNPt", "ulbKateyaNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtGopalganj, baseHeads, createULBUserGroup("Nagar Panchayat,Kateya")));

		}

		// NAGAR_PANCHAYAT Saran division end here

		Optional<GeoDistrictEntity> disNPtSupaulO = districtRepo.findByNameAndDivision_Name("Supaul", "Koshi");
		if (disNPtSupaulO.isPresent()) {
			GeoDistrictEntity disNPtSupaul = disNPtSupaulO.get();
			saveAllTypeUlbs(getULB("Nagar Panchayat,Birpur", "ulbBirpurNPt", "ulbBirpurNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtSupaul, baseHeads, createULBUserGroup("Nagar Panchayat,Birpur")));
			saveAllTypeUlbs(getULB("Nagar Panchayat,Nirmali", "ulbNirmaliNPt", "ulbNirmaliNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtSupaul, baseHeads, createULBUserGroup("Nagar Panchayat,Nirmali")));

		}

		Optional<GeoDistrictEntity> disNPtMadhepuraO = districtRepo.findByNameAndDivision_Name("Madhepura", "Koshi");
		if (disNPtMadhepuraO.isPresent()) {
			GeoDistrictEntity disNPtMadhepura = disNPtMadhepuraO.get();
			saveAllTypeUlbs(
					getULB("Nagar Panchayat,Murliganj", "ulbMurliganjNPt", "ulbMurliganjNPt", ULBType.NAGAR_PANCHAYAT,
							disNPtMadhepura, baseHeads, createULBUserGroup("Nagar Panchayat,Murliganj")));

		}

		Optional<GeoDistrictEntity> disNPtSaharsaO = districtRepo.findByNameAndDivision_Name("Saharsa", "Koshi");
		if (disNPtSaharsaO.isPresent()) {
			GeoDistrictEntity disNPtSaharsa = disNPtSaharsaO.get();
			saveAllTypeUlbs(
					getULB("Nagar Panchayat,Simri Bakhtiyarpur", "ulbSimriNPt", "ulbSimriNPt", ULBType.NAGAR_PANCHAYAT,
							disNPtSaharsa, baseHeads, createULBUserGroup("Nagar Panchayat,Simri Bakhtiyarpur")));

		}

		// koshi divisions nagar panchayat ulbs ends

		Optional<GeoDistrictEntity> disNPtPurniaO = districtRepo.findByNameAndDivision_Name("Purnia", "Purnia");
		if (disNPtPurniaO.isPresent()) {
			GeoDistrictEntity disNPtPurnia = disNPtPurniaO.get();
			saveAllTypeUlbs(getULB("Nagar Panchayat,Kasba", "ulbKasbaNPt", "ulbKasbaNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtPurnia, baseHeads, createULBUserGroup("Nagar Panchayat,Kasba")));
			saveAllTypeUlbs(getULB("Nagar Panchayat,Banmankhi", "ulbBanmankhiNPt", "ulbBanmankhiNPt",
					ULBType.NAGAR_PANCHAYAT, disNPtPurnia, baseHeads, createULBUserGroup("Nagar Panchayat,Banmankhi")));

		}

		Optional<GeoDistrictEntity> disNPtArariaO = districtRepo.findByNameAndDivision_Name("Araria", "Purnia");
		if (disNPtArariaO.isPresent()) {
			GeoDistrictEntity disNPtAraria = disNPtArariaO.get();
			saveAllTypeUlbs(getULB("Nagar Panchayat,Jogbani", "ulbJogbaniNPt", "ulbJogbaniNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtAraria, baseHeads, createULBUserGroup("Nagar Panchayat,Jogbani")));

		}
		Optional<GeoDistrictEntity> disNPtKishanganjO = districtRepo.findByNameAndDivision_Name("Kishanganj", "Purnia");
		if (disNPtKishanganjO.isPresent()) {
			GeoDistrictEntity disNPtKishanganj = disNPtKishanganjO.get();
			saveAllTypeUlbs(getULB("Nagar Panchayat,Bahadurganj", "ulbBahadurganjNPt", "ulbBahadurganjNPt",
					ULBType.NAGAR_PANCHAYAT, disNPtKishanganj, baseHeads,
					createULBUserGroup("Nagar Panchayat,Bahadurganj")));
			saveAllTypeUlbs(getULB("Nagar Panchayat,Thakurganj", "ulbThakurganjNPt", "ulbThakurganjNPt",
					ULBType.NAGAR_PANCHAYAT, disNPtKishanganj, baseHeads,
					createULBUserGroup("Nagar Panchayat,Thakurganj")));

		}

		Optional<GeoDistrictEntity> disNPtKatiharO = districtRepo.findByNameAndDivision_Name("Katihar", "Purnia");
		if (disNPtKatiharO.isPresent()) {
			GeoDistrictEntity disNPtKatihar = disNPtKatiharO.get();
			saveAllTypeUlbs(getULB("Nagar Panchayat,Manihari", "ulbManihariNPt", "ulbManihariNPt",
					ULBType.NAGAR_PANCHAYAT, disNPtKatihar, baseHeads, createULBUserGroup("Nagar Panchayat,Manihari")));
			saveAllTypeUlbs(getULB("Nagar Panchayat,Barsoi", "ulbBarsoiNPt", "ulbBarsoiNPt", ULBType.NAGAR_PANCHAYAT,
					disNPtKatihar, baseHeads, createULBUserGroup("Nagar Panchayat,Barsoi")));

		}

		// NAGAR_PANCHAYAT Purnia division end here

	}

	private ULBEntity getULB(String name, String code, String alias, ULBType type, GeoDistrictEntity district,
			List<BaseHeadEntity> baseHead, List<UserGroupEntity> userGroups) {
		return ULBEntity.builder().active(true).aliasName(alias).code(code).district(district).name(name).type(type)
				//.userProfiles(null)
				.userGroups(userGroups).build();
	}

	private void saveAllTypeUlbs(ULBEntity ulbEntity) {
		ULBEntity ulb = ulbRepo.save(ulbEntity);
		ulb.setUserGroups(createULBUserGroup(ulb.getName()));
		ulbRepo.save(ulb);
	}

	private List<UserGroupEntity> createULBUserGroup(String name) {
		String userGroup = "_USER_GROUP";
		UserGroupEntity ug = new UserGroupEntity();
		ug.setName(name.toUpperCase().concat(userGroup));
		ug.setStatus(true);
		ug = userGrpRepo.save(ug);
		List<UserGroupEntity> ugs = new ArrayList<UserGroupEntity>();
		ugs.add(ug);
		return ugs;
	}

}
