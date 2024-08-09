package com.scsinfinity.udhd.configurations.dbinitializers;

import java.util.Arrays;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.scsinfinity.udhd.dao.entities.geography.GeoDistrictEntity;
import com.scsinfinity.udhd.dao.entities.geography.GeoDivisionEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBEntity;
import com.scsinfinity.udhd.dao.entities.geography.ULBType;
import com.scsinfinity.udhd.dao.repositories.geography.IGeoDistrictRepository;
import com.scsinfinity.udhd.dao.repositories.geography.IGeoDivisionRepository;
import com.scsinfinity.udhd.dao.repositories.geography.IULBRepository;

import lombok.AllArgsConstructor;

@Component
@Transactional
@AllArgsConstructor
public class GeographyInitializer {
	private final IGeoDivisionRepository divisionRepo;
	private final IGeoDistrictRepository districtRepo;
	private final IULBRepository ulbRepo;

	public void initializeGeography() throws Exception {
		initializeDivison();
		saveDistrict();
		saveULB();
	}

	private void initializeDivison() {
		divisionRepo.saveAll(Arrays.asList(new GeoDivisionEntity("Patna", "Pat", true),
				new GeoDivisionEntity("Magadh", "Magadh", true), new GeoDivisionEntity("Bhagalpur", "Bha", true),
				new GeoDivisionEntity("Tirhut", "Tir", true), new GeoDivisionEntity("Darbhanga", "Dar", true),
				new GeoDivisionEntity("Munger", "Munger", true), new GeoDivisionEntity("Purnia", "Purnia", true),
				new GeoDivisionEntity("Saran", "Saran", true), new GeoDivisionEntity("Koshi", "Koshi", true)));
	}

	private void saveDistrict() {
		Optional<GeoDivisionEntity> divPatnaO = divisionRepo.findByNameIgnoreCase("Patna");
		if (divPatnaO.isPresent()) {
			GeoDivisionEntity divPatna = divPatnaO.get();

			districtRepo.save(new GeoDistrictEntity("Patna", "Patna", true, divPatna));
			districtRepo.save(new GeoDistrictEntity("Bhojpur", "bhojpur", true, divPatna));
			districtRepo.save(new GeoDistrictEntity("Rohtas", "rohtas", true, divPatna));
			districtRepo.save(new GeoDistrictEntity("Kaimur", "kaimur", true, divPatna));
			districtRepo.save(new GeoDistrictEntity("Nalanda", "nalanda", true, divPatna));
			districtRepo.save(new GeoDistrictEntity("Buxar", "buxar", true, divPatna));

		}
		Optional<GeoDivisionEntity> divMagadhO = divisionRepo.findByNameIgnoreCase("Magadh");
		if (divMagadhO.isPresent()) {
			GeoDivisionEntity divMagadh = divMagadhO.get();
			districtRepo.save(new GeoDistrictEntity("Gaya", "gaya", true, divMagadh));
			districtRepo.save(new GeoDistrictEntity("Jehanabad", "Jehanabad", true, divMagadh));
			districtRepo.save(new GeoDistrictEntity("Aurangabad", "Aurangabad", true, divMagadh));
			districtRepo.save(new GeoDistrictEntity("Nawada", "nawada", true, divMagadh));
			districtRepo.save(new GeoDistrictEntity("Arwal", "arwal", true, divMagadh));

		}

		Optional<GeoDivisionEntity> divBhagalpurO = divisionRepo.findByNameIgnoreCase("Bhagalpur");
		if (divBhagalpurO.isPresent()) {
			GeoDivisionEntity divBhagalpur = divBhagalpurO.get();
			districtRepo.save(new GeoDistrictEntity("Bhagalpur", "bhagalpur", true, divBhagalpur));
			districtRepo.save(new GeoDistrictEntity("Banka", "banka", true, divBhagalpur));
		}

		Optional<GeoDivisionEntity> divTirhutO = divisionRepo.findByNameIgnoreCase("Tirhut");
		if (divTirhutO.isPresent()) {
			GeoDivisionEntity divTirhut = divTirhutO.get();

			districtRepo.save(new GeoDistrictEntity("Sitamarhi", "sitamarhi", true, divTirhut));
			districtRepo.save(new GeoDistrictEntity("Muzaffarpur", "muzaffar", true, divTirhut));

			districtRepo.save(new GeoDistrictEntity("Vaishali", "vaishali", true, divTirhut));
			districtRepo.save(new GeoDistrictEntity("Sheohar", "sheohar", true, divTirhut));

			districtRepo.save(new GeoDistrictEntity("East Champaran", "purvi", true, divTirhut));
			districtRepo.save(new GeoDistrictEntity("West Champaran", "WestChamp", true, divTirhut));

		}

		Optional<GeoDivisionEntity> divDarbhangaO = divisionRepo.findByNameIgnoreCase("Darbhanga");
		if (divDarbhangaO.isPresent()) {
			GeoDivisionEntity divDarbhanga = divDarbhangaO.get();

			districtRepo.save(new GeoDistrictEntity("Madhubani", "madhubani", true, divDarbhanga));
			districtRepo.save(new GeoDistrictEntity("Samastipur", "samastipur", true, divDarbhanga));
			districtRepo.save(new GeoDistrictEntity("Darbhanga", "darbhanga", true, divDarbhanga));
		}

		Optional<GeoDivisionEntity> divMungerO = divisionRepo.findByNameIgnoreCase("Munger");
		if (divMungerO.isPresent()) {
			GeoDivisionEntity divMunger = divMungerO.get();
			districtRepo.save(new GeoDistrictEntity("Munger", "munger", true, divMunger));
			districtRepo.save(new GeoDistrictEntity("Lakhisarai", "lakhisarai", true, divMunger));
			districtRepo.save(new GeoDistrictEntity("Jamui", "jamui", true, divMunger));
			districtRepo.save(new GeoDistrictEntity("Khagaria", "khagaria", true, divMunger));
			districtRepo.save(new GeoDistrictEntity("Begusarai", "begusarai", true, divMunger));
			districtRepo.save(new GeoDistrictEntity("Sheikhpura", "sheikhpura", true, divMunger));

		}

		Optional<GeoDivisionEntity> divSaranO = divisionRepo.findByNameIgnoreCase("Saran");
		if (divSaranO.isPresent()) {
			GeoDivisionEntity divSaran = divSaranO.get();
			districtRepo.save(new GeoDistrictEntity("Saran", "saran", true, divSaran));
			districtRepo.save(new GeoDistrictEntity("Siwan", "siwan", true, divSaran));
			districtRepo.save(new GeoDistrictEntity("Gopalganj", "gopalganj", true, divSaran));

		}

		Optional<GeoDivisionEntity> divKoshiO = divisionRepo.findByNameIgnoreCase("Koshi");
		if (divKoshiO.isPresent()) {
			GeoDivisionEntity divKoshi = divKoshiO.get();
			districtRepo.save(new GeoDistrictEntity("Supaul", "supaul", true, divKoshi));
			districtRepo.save(new GeoDistrictEntity("Saharsa", "saharsa", true, divKoshi));
			districtRepo.save(new GeoDistrictEntity("Madhepura", "madhepura", true, divKoshi));
		}

		Optional<GeoDivisionEntity> divPurniyaO = divisionRepo.findByNameIgnoreCase("Purnia");
		if (divPurniyaO.isPresent()) {
			GeoDivisionEntity divPurniya = divPurniyaO.get();
			districtRepo.save(new GeoDistrictEntity("Purnia", "purnia", true, divPurniya));
			districtRepo.save(new GeoDistrictEntity("Araria", "araria", true, divPurniya));
			districtRepo.save(new GeoDistrictEntity("Kishanganj", "kishanganj", true, divPurniya));
			districtRepo.save(new GeoDistrictEntity("Katihar", "katihar", true, divPurniya));

		}

	}

	void saveInidividualULB(String districtName, String divisionName, String ulbName, String ulbCode, String alias,
			ULBType type) {
		Optional<GeoDistrictEntity> districtO = districtRepo.findByNameIgnoreCaseAndDivision_NameIgnoreCase(districtName, divisionName);
		if (districtO.isPresent()) {

			GeoDistrictEntity district = districtO.get();
			ULBEntity ulb = ULBEntity.builder().name(ulbName).code(ulbCode).aliasName(alias).type(type).active(true)
					.district(district).build();
			ULBEntity ulbPatna = ulbRepo.save(ulb);
			ulbRepo.save(ulbPatna);
		}
	}

	private void saveULB() {
		// Municipal Corporation
		saveInidividualULB("Patna", "Patna", "Patna Nagar Nigam", "ulbpatna", "ulbpatna",
				ULBType.MUNICIPAL_CORPORATION);
		saveInidividualULB("Nalanda", "Patna", "BiharSarif Nagar Nigam", "ulbsarif", "ulbsarif",
				ULBType.MUNICIPAL_CORPORATION);
		saveInidividualULB("Bhojpur", "Patna", "Arra Nagar Nigam", "ulbarra", "ulbarra", ULBType.MUNICIPAL_CORPORATION);
		saveInidividualULB("Gaya", "Magadh", "Gaya Nagar Nigam", "ulbgaya", "ulbgaya", ULBType.MUNICIPAL_CORPORATION);
		saveInidividualULB("Bhagalpur", "Bhagalpur", "Bhagalpur Nagar Nigam", "bhagalpur", "ulbbhagalpur",
				ULBType.MUNICIPAL_CORPORATION);
		saveInidividualULB("Muzaffarpur", "Tirhut", "Muzaffarpur Nagar Nigam", "muzafarpur", "Muzaffarpur",
				ULBType.MUNICIPAL_CORPORATION);
		saveInidividualULB("Darbhanga", "Darbhanga", "Darbhanga Nagar Nigam", "darbhanga", "darbhanga",
				ULBType.MUNICIPAL_CORPORATION);
		saveInidividualULB("Begusarai", "Munger", "Begusarai Nagar Nigam", "ulbbegusarai", "ulbbegusarai",
				ULBType.MUNICIPAL_CORPORATION);
		saveInidividualULB("Munger", "Munger", "Munger Nagar Nigam", "ulbMunger", "ulbMunger",
				ULBType.MUNICIPAL_CORPORATION);
		saveInidividualULB("Purnia", "Purnia", "Purnia Nagar Nigam", "ulbPurnia", "ulbPurnia",
				ULBType.MUNICIPAL_CORPORATION);
		saveInidividualULB("Katihar", "Purnia", "Katihar Nagar Nigam", "ulbKatihar", "ulbKatihar",
				ULBType.MUNICIPAL_CORPORATION);

		// MUNICIPAL_COUNCIL type ulbs start here

		saveInidividualULB("Patna", "Patna", "Nagar Parisad,Barh", "ulbBarhNP", "ulbBarhNP", ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Patna", "Patna", "Nagar Parisad,Khagaul", "ulbKhagaulNP", "ulbKhagaulNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Patna", "Patna", "Nagar Parisad,Danapur", "ulbDanapurNP", "ulbDanapurNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Patna", "Patna", "Nagar Parisad,Masaurhi", "ulbMasaurhiNP", "ulbMasaudiNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Patna", "Patna", "Nagar Parisad,Mokama", "ulbMokamaNP", "ulbMokamaNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Patna", "Patna", "Nagar Parisad,PhulwariSarif", "ulbPhulwariNP", "ulbPhulwariNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Patna", "Patna", "Nagar Parisad,Bakhtiyarpur", "ulbBakhtiyarpurNP", "ulbBakhtiyarpurNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Patna", "Patna", "Nagar Parisad,Fatuha", "ulbFatuhaNP", "ulbFatuhaNP",
				ULBType.MUNICIPAL_COUNCIL);

		saveInidividualULB("Buxar", "Patna", "Nagar Parisad,Buxar", "ulbBuxarNP", "ulbBuxarNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Buxar", "Patna", "Nagar Parisad,Dumrao", "ulbDumraoNP", "ulbBadhNP",
				ULBType.MUNICIPAL_COUNCIL);

		saveInidividualULB("Rohtas", "Patna", "Nagar Parisad,Sasaram", "ulbSasaramNP", "ulbSasaramNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Rohtas", "Patna", "Nagar Parisad,Dehri-Dalmianagar", "ulbDalmiaNP", "ulbDalmiaNP",
				ULBType.MUNICIPAL_COUNCIL);

		saveInidividualULB("Kaimur", "Patna", "Nagar Parisad,Bhabhua", "ulbBhabhuaNP", "ulbBhabhuaNP",
				ULBType.MUNICIPAL_COUNCIL);

		saveInidividualULB("Nalanda", "Patna", "Nagar Parisad,Hilsa", "ulbHilsaNP", "ulbHilsaNP",
				ULBType.MUNICIPAL_COUNCIL);

		// patna division ulbs end here

		// magadh division ulb start here

		saveInidividualULB("Jehanabad", "Magadh", "Nagar Parisad,Jehanabad", "ulbJehanabadNP", "ulbJehanabadNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Arwal", "Magadh", "Nagar Parisad,Arwal", "ulbArwalNP", "ulbArwalNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Aurangabad", "Magadh", "Nagar Parisad,Aurangabad", "ulbAurangabadNP", "ulbAurangabadNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Nawada", "Magadh", "Nagar Parisad,Nawada", "ulbNawadaNP", "ulbNawadaNP",
				ULBType.MUNICIPAL_COUNCIL);

		// magadh division ulb end here

		// Tirhut division ulb start here
		saveInidividualULB("Sitamarhi", "Tirhut", "Nagar Parisad,Sitamarhi", "ulbSitamarhiNP", "ulbSitamarhiNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Vaishali", "Tirhut", "Nagar Parisad,Hajipur", "ulbHajipurNP", "ulbHajipurNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("East Champaran", "Tirhut", "Nagar Parisad,Motihari", "ulbMotihariNP", "ulbMotihariNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("East Champaran", "Tirhut", "Nagar Parisad,Raksaul", "ulbRaksaulNP", "ulbRaksaulNP",
				ULBType.MUNICIPAL_COUNCIL);

		saveInidividualULB("West Champaran", "Tirhut", "Nagar Parisad,Bettiah", "ulbBettiahNP", "ulbBettiahNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("West Champaran", "Tirhut", "Nagar Parisad,Baaha", "ulbBaghaNP", "ulbBaghaNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("West Champaran", "Tirhut", "Nagar Parisad,Narkatiaganj", "ulbNarkatiaganjNP",
				"ulbNarkatiaganjNP", ULBType.MUNICIPAL_COUNCIL);

		// tirhut divisions ulb end here

		// Darbhanga divisions ulb start here
		saveInidividualULB("Darbhanga", "Darbhanga", "Nagar Parisad,Benipur", "ulbBenipurNP", "ulbBenipurNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Madhubani", "Darbhanga", "Nagar Parisad,Madhubani", "ulbMadhubaniNP", "ulbMadhubaniNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Samastipur", "Darbhanga", "Nagar Parisad,Samastipur", "ulbSamastipurNP", "ulbSamastipurNP",
				ULBType.MUNICIPAL_COUNCIL);

		// Darbhanga divisions end

		// bhagalpur divisions start
		saveInidividualULB("Bhagalpur", "Bhagalpur", "Nagar Parisad,Sultanganj", "ulbSultanganjNP", "ulbSultanganjNP",
				ULBType.MUNICIPAL_COUNCIL);

		// bhagalpur divisions end

		// Munger divisions start

		saveInidividualULB("Munger", "Munger", "Nagar Parisad,Jamalpur", "ulbJamalpurNP", "ulbJamalpurNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Lakhisarai", "Munger", "Nagar Parisad,Lakhisarai", "ulbLakhisaraiNP", "ulbLakhisaraiNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Sheikhpura", "Munger", "Nagar Parisad,Sheikhpura", "ulbSheikhpuraNP", "ulbSheikhpuraNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Jamui", "Munger", "Nagar Parisad,Jamui", "ulbJamuiNP", "ulbJamuiNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Khagaria", "Munger", "Nagar Parisad,Khagaria", "ulbKhagariaNP", "ulbKhagariaNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Begusarai", "Munger", "Nagar Parisad,Bihat", "ulbBihatNP", "ulbBihatNP",
				ULBType.MUNICIPAL_COUNCIL);

		// munger division ulbs end here

		// Saran division ulbs start here
		saveInidividualULB("Saran", "Saran", "Nagar Parisad,Chhapra", "ulbChhapraNP", "ulbChhapraNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Siwan", "Saran", "Nagar Parisad,Siwan", "ulbSiwanNP", "ulbSiwanNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Gopalganj", "Saran", "Nagar Parisad,Gopalganj", "ulbGopalganjNP", "ulbGopalganjNP",
				ULBType.MUNICIPAL_COUNCIL);

		// saran divisions end

		// koshi division start

		saveInidividualULB("Saharsa", "Koshi", "Nagar Parisad,Saharsa", "ulbSaharsaNP", "ulbSaharsaNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Madhepura", "Koshi", "Nagar Parisad,Madhepura", "ulbMadhepuraNP", "ulbMadhepuraNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Gopalganj", "Saran", "Nagar Parisad,Supaul", "ulbSupaulNP", "ulbSupaulNP",
				ULBType.MUNICIPAL_COUNCIL);

		// koshi division ulbs end here

		// purnia divisions

		saveInidividualULB("Araria", "Purnia", "Nagar Parisad,Araria", "ulbArariaNP", "ulbArariaNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Araria", "Purnia", "Nagar Parisad,Forbesganj", "ulbForbesganjNP", "ulbForbesganjNP",
				ULBType.MUNICIPAL_COUNCIL);
		saveInidividualULB("Kishanganj", "Purnia", "Nagar Parisad,Kishanganj", "ulbKishanganjNP", "ulbKishanganjNP",
				ULBType.MUNICIPAL_COUNCIL);

		// purnia divisions end heres

		// municipal council type ulbs complete

		// Nagar Panchayat Ulbs type start
		saveInidividualULB("Patna", "Patna", "Nagar Panchayat,Maner", "ulbManerNP", "ulbManerNP",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Patna", "Patna", "Nagar Panchayat,Khushrupur", "ulbKhushrupurNPt", "ulbKhushrupurNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Patna", "Patna", "Nagar Panchayat,Bikram", "ulbBikramNPt", "ulbBikramNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Patna", "Patna", "Nagar Panchayat,Naubatpur", "ulbNaubatpurNPt", "ulbNaubatpurNPt",
				ULBType.NAGAR_PANCHAYAT);

		// patna district nagar panchayat end

		saveInidividualULB("Bhojpur", "Patna", "Nagar Panchayat,Piro", "ulbPiroNPt", "ulbPiroNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Bhojpur", "Patna", "Nagar Panchayat,Bihiyan", "ulbBihiyanNPt", "ulbBihiyanNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Bhojpur", "Patna", "Nagar Panchayat,Jagdishpur", "ulbJagdishpurNPt", "ulbJagdishpurNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Bhojpur", "Patna", "Nagar Panchayat,Koilwar", "ulbKoilwarNPt", "ulbKoilwarNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Bhojpur", "Patna", "Nagar Panchayat,Shahpur", "ulbShahpurNPt", "ulbShahpurNPt",
				ULBType.NAGAR_PANCHAYAT);

		saveInidividualULB("Rohtas", "Patna", "Nagar Panchayat,Koath", "ulbKoathNPt", "ulbKoathNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Rohtas", "Patna", "Nagar Panchayat,Nokha", "ulbNokhaNPt", "ulbNokhaNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Rohtas", "Patna", "Nagar Panchayat,Bikramganj", "ulBikramganjNPt", "ulbBikramganjNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Rohtas", "Patna", "Nagar Panchayat,Nasriganj", "ulbNasriganjNPt", "ulbNasriganjNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Rohtas", "Patna", "Nagar Panchayat,Kochas", "ulbKochasNPt", "ulbKochasNPt",
				ULBType.NAGAR_PANCHAYAT);

		saveInidividualULB("Kaimur", "Patna", "Nagar Panchayat,Mohania", "ulbMohaniaNPt", "ulbMohaniaNPt",
				ULBType.NAGAR_PANCHAYAT);

		saveInidividualULB("Nalanda", "Patna", "Nagar Panchayat,Islampur", "ulbIslampurNPt", "ulbIslampurNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Nalanda", "Patna", "Nagar Panchayat,Silao", "ulbSilaoNPt", "ulbSilaoNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Nalanda", "Patna", "Nagar Panchayat,Rajgir", "ulbRajgirNPt", "ulbRajgirNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Nalanda", "Patna", "Nagar Panchayat,Harnaut", "ulbHarnautNPt", "ulbHarnautNPt",
				ULBType.NAGAR_PANCHAYAT);

		// NAGAR_PANCHAYAT patna division end here
		saveInidividualULB("Gaya", "Magadh", "Nagar Panchayat,Bodhgaya", "ulbBodhgayaNPt", "ulbBodhgayaNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Gaya", "Magadh", "Nagar Panchayat,Sherghati", "ulbSherghatiNPt", "ulbSherghatiNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Gaya", "Magadh", "Nagar Panchayat,Tekari", "ulbTekariNPt", "ulbTekariNPt",
				ULBType.NAGAR_PANCHAYAT);

		saveInidividualULB("Jahanabad", "Magadh", "Nagar Panchayat,Makhdumpur", "ulbMakhdumpurNPt", "ulbMakhdumpurNPt",
				ULBType.NAGAR_PANCHAYAT);

		saveInidividualULB("Aurangabad", "Magadh", "Nagar Panchayat,Rafiganj", "ulbRafiganjNPt", "ulbRafiganjNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Aurangabad", "Magadh", "Nagar Panchayat,Navinagar", "ulbNavinagarNPt", "ulbNavinagarNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Aurangabad", "Magadh", "Nagar Panchayat,Daudnagar", "ulbDaudnagarNPt", "ulbDaudnagarNPt",
				ULBType.NAGAR_PANCHAYAT);

		saveInidividualULB("Nawada", "Magadh", "Nagar Panchayat,Warsaliganj", "ulbWganjNPt", "ulbWganjNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Nawada", "Magadh", "Nagar Panchayat,Hisua", "ulbHisuaNPt", "ulbHisuaNPt",
				ULBType.NAGAR_PANCHAYAT);

		// NAGAR_PANCHAYAT Magadh division end here

		saveInidividualULB("Bhagalpur", "Bhagalpur", "Nagar Panchayat,Naugachia", "ulbNaugachiaNPt", "ulNaugachiaNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Bhagalpur", "Bhagalpur", "Nagar Panchayat,Kahalgaon", "ulbKahalgaonNPt", "ulbKahalgaonNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Bhagalpur", "Bhagalpur", "Nagar Panchayat,Amarpur", "ulbAmarpurNPt", "ulbAmarpurNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Bhagalpur", "Bhagalpur", "Nagar Panchayat,Banka", "ulbBankaNPt", "ulbBankaNPt",
				ULBType.NAGAR_PANCHAYAT);

		// NAGAR_PANCHAYAT Bhagalpur division end here
		saveInidividualULB("Sitamarhi", "Tirhut", "Nagar Panchayat,Bairgania", "ulbBairganiaNPt", "ulbBairganiaNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Sitamarhi", "Tirhut", "Nagar Panchayat,Belsand", "ulbBelsandNPt", "ulbBelsandNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Sitamarhi", "Tirhut", "Nagar Panchayat,Dumra", "ulbDumraNPt", "ulbDumraNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Sitamarhi", "Tirhut", "Nagar Panchayat,Janakpur Road", "ulbJanakpurNPt", "ulbJanakpurNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Sitamarhi", "Tirhut", "Nagar Panchayat,Sursand", "ulbSursandNPt", "ulbSursandNPt",
				ULBType.NAGAR_PANCHAYAT);

		saveInidividualULB("Muzaffarpur", "Tirhut", "Nagar Panchayat,Motipur", "ulbMotipurNPt", "ulbMotipurNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Muzaffarpur", "Tirhut", "Nagar Panchayat,Kati", "ulbKantiNPt", "ulbKantiNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Muzaffarpur", "Tirhut", "Nagar Panchayat,Shahebganj", "ulbShahebganjNPt",
				"ulbShahebganjNPt", ULBType.NAGAR_PANCHAYAT);

		saveInidividualULB("Vaishali", "Tirhut", "Nagar Panchayat,Lalganj", "ulbLalganjNPt", "ulLalganjNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Vaishali", "Tirhut", "Nagar Panchayat,Mahua", "ulbMahuaNPt", "ulbMahuaNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Vaishali", "Tirhut", "Nagar Panchayat,Mahnar", "ulbMahnarNPt", "ulbMahnarNPt",
				ULBType.NAGAR_PANCHAYAT);

		saveInidividualULB("Sheohar", "Tirhut", "Nagar Panchayat,Sheohar", "ulbSheoharNPt", "ulbSheoharNPt",
				ULBType.NAGAR_PANCHAYAT);

		saveInidividualULB("East Champaran", "Tirhut", "Nagar Panchayat,Chakiya", "ulbChakiyaNPt", "ulbChakiyaNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("East Champaran", "Tirhut", "Nagar Panchayat,Sugauli", "ulbSugauliNPt", "ulbSugauliNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("East Champaran", "Tirhut", "Nagar Panchayat,Areraj", "ulbArerajNPt", "ulbArerajNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("East Champaran", "Tirhut", "Nagar Panchayat,Kesariya", "ulbKesariaNPt", "ulbKesariaNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("East Champaran", "Tirhut", "Nagar Panchayat,Pakaridayal", "ulbPakaridayalNPt",
				"ulbPakaridayalNPt", ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("East Champaran", "Tirhut", "Nagar Panchayat,Mehsi", "ulbMehsiNPt", "ulbMehsilNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("East Champaran", "Tirhut", "Nagar Panchayat,Dhaka", "ulbDhakaNPt", "ulbDhakaNPt",
				ULBType.NAGAR_PANCHAYAT);

		saveInidividualULB("West Champaran", "Tirhut", "Nagar Panchayat,Chanpatia", "ulbChanpatiaNPt",
				"ulbChanpatiaNPt", ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("West Champaran", "Tirhut", "Nagar Panchayat,Ramnagar", "ulbRamnagarNPt", "ulRamnagarNPt",
				ULBType.NAGAR_PANCHAYAT);

		// NAGAR_PANCHAYAT tirhut division end here

		saveInidividualULB("Madhubani", "Darbhanga", "Nagar Panchayat,Jainagar", "ulbJainagarNPt", "ulbJainagarNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Madhubani", "Darbhanga", "Nagar Panchayat,Jhanjharpur", "ulbJhanjharpurNPt",
				"ulbJhanjharpurNPt", ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Madhubani", "Darbhanga", "Nagar Panchayat,Ghoghardiha", "ulbGhoghardihaNPt",
				"ulbGhoghardihaNPt", ULBType.NAGAR_PANCHAYAT);

		saveInidividualULB("Samastipur", "Darbhanga", "Nagar Panchayat,Dalsinghsarai", "ulbDalsinghsaraihNPt",
				"ulbDalsinghsaraiNPt", ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Samastipur", "Darbhanga", "Nagar Panchayat,Rosera", "ulbRoseraNPt", "ulbRoseraNPt",
				ULBType.NAGAR_PANCHAYAT);

		// NAGAR_PANCHAYAT Darbhanga division end here
		saveInidividualULB("Munger", "Munger", "Nagar Panchayat,Haveli Kharagpur", "ulbKharagpurNPt", "ulbKharagpurNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Lakhisarai", "Munger", "Barahiya Nagar Panchayat", "ulbBarahiyaNPt", "ulbBarahiyaNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Jamui", "Munger", "Nagar Panchayat Jhajha", "ulbJhajhaNPt", "ulbJhajhaNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Sheikhpura", "Munger", "Nagar Panchayat,Barbigha", "ulbBarbighaNPt", "ulbBarbighaNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Khagaria", "Munger", "Nagar Panchayat,Gogri Jamalpur", "ulbGogriNPt", "ulbGogriNPt",
				ULBType.NAGAR_PANCHAYAT);

		saveInidividualULB("Begusarai", "Munger", "Nagar Panchayat,Bakhri", "ulbBakhriNPt", "ulbBakhriNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Begusarai", "Munger", "Nagar Panchayat,Teghra", "ulbTeghraNPt", "ulbTeghraNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Begusarai", "Munger", "Nagar Panchayat,Baliya", "ulbBaliaNPt", "ulbBaliaNPt",
				ULBType.NAGAR_PANCHAYAT);

		// NAGAR_PANCHAYAT Munger division end here
		saveInidividualULB("Saran", "Saran", "Nagar Panchayat,Sonpur", "ulbSonpurNPt", "ulbSonpurNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Saran", "Saran", "Nagar Panchayat,Dighwara", "ulbDighwaraNPt", "ulbDighwaraNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Saran", "Saran", "Nagar Panchayat,Marhaura", "ulbMarhauraNPt", "ulbMarhauraNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Saran", "Saran", "Nagar Panchayat,Rivilganj", "ulbRivilganjNPt", "ulRivilganjNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Saran", "Saran", "Nagar Panchayat,Ekma Bazar", "ulbEkmaNPt", "ulbEkmaNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Saran", "Saran", "Nagar Panchayat,Parsa Bazar", "ulbParsaNPt", "ulbParsaNPt",
				ULBType.NAGAR_PANCHAYAT);

		saveInidividualULB("Siwan", "Saran", "Nagar Panchayat,Maharajganj", "ulbMaharajganjNPt", "ulbMaharajganjNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Siwan", "Saran", "Nagar Panchayat,Mairwan", "ulbMairwanNPt", "ulbMairwanNPt",
				ULBType.NAGAR_PANCHAYAT);

		saveInidividualULB("Gopalganj", "Saran", "Nagar Panchayat,Mirganj", "ulbMirganjNPt", "ulbMirganjNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Gopalganj", "Saran", "Nagar Panchayat,Barauli", "ulbBarauliNPt", "ulbBarauliNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Gopalganj", "Saran", "Nagar Panchayat,Kateya", "ulbKateyaNPt", "ulbKateyaNPt",
				ULBType.NAGAR_PANCHAYAT);

		// NAGAR_PANCHAYAT Saran division end here
		saveInidividualULB("Supaul", "Koshi", "Nagar Panchayat,Birpur", "ulbBirpurNPt", "ulbBirpurNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Supaul", "Koshi", "Nagar Panchayat,Nirmali", "ulbNirmaliNPt", "ulbNirmaliNPt",
				ULBType.NAGAR_PANCHAYAT);

		saveInidividualULB("Madhepura", "Koshi", "Nagar Panchayat,Murliganj", "ulbMurliganjNPt", "ulbMurliganjNPt",
				ULBType.NAGAR_PANCHAYAT);

		saveInidividualULB("Saharsa", "Koshi", "Nagar Panchayat,Simri Bakhtiyarpur", "ulbSimriNPt", "ulbSimriNPt",
				ULBType.NAGAR_PANCHAYAT);

		// koshi divisions nagar panchayat ulbs ends

		saveInidividualULB("Purnia", "Purnia", "Nagar Panchayat,Kasba", "ulbKasbaNPt", "ulbKasbaNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Purnia", "Purnia", "Nagar Panchayat,Banmankhi", "ulbBanmankhiNPt", "ulbBanmankhiNPt",
				ULBType.NAGAR_PANCHAYAT);

		saveInidividualULB("Araria", "Purnia", "Nagar Panchayat,Jogbani", "ulbJogbaniNPt", "ulbJogbaniNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Kishanganj", "Purnia", "Nagar Panchayat,Bahadurganj", "ulbBahadurganjNPt",
				"ulbBahadurganjNPt", ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Kishanganj", "Purnia", "Nagar Panchayat,Thakurganj", "ulbThakurganjNPt", "ulbThakurganjNPt",
				ULBType.NAGAR_PANCHAYAT);

		saveInidividualULB("Katihar", "Purnia", "Nagar Panchayat,Manihari", "ulbManihariNPt", "ulbManihariNPt",
				ULBType.NAGAR_PANCHAYAT);
		saveInidividualULB("Katihar", "Purnia", "Nagar Panchayat,Barsoi", "ulbBarsoiNPt", "ulbBarsoiNPt",
				ULBType.NAGAR_PANCHAYAT);

	}

}
