package businesscomponents;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import frutils.FieldValidationLib;
import frutils.MiscLib;
import frutils.ObjEmulator;
import frutils.ReportingHtmlLib;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;
import ObjectRepository.*;

public class SellerAssignmentEntry extends ReusableLibrary {

	public SellerAssignmentEntry(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	ReportingHtmlLib dl = new ReportingHtmlLib();
	MiscLib msc = new MiscLib();
	ObjEmulator objEmu = new ObjEmulator();
	ReusablesSeller reusablesSeller = new ReusablesSeller(scriptHelper);
	String sCurrThread = null;
	String sDesc = null;
	FieldValidationLib fd = new FieldValidationLib(scriptHelper);
	String sFieldDisplayStatus = "";

	public void assignmentEntry() {

		sCurrThread = dataTable.getData("AssignmentEntry", "TC_ID");
		sDesc = dataTable.getData("AssignmentEntry", "Description");
		try {
			dl.writeHtmlTag(sCurrThread, "<h3>" + sCurrThread + "</h3>");
			dl.writeHtmlTag(sCurrThread, "<h3>" + sDesc + "</h3>");
			dl.writeHtmlTag(sCurrThread, "<table border=\"1\">");
			System.out.println(sCurrThread);
			System.out.println(sDesc);

			String sUsername = dataTable.getData("AssignmentEntry", "Username");
			String sPassword = dataTable.getData("AssignmentEntry", "Password");
			String sCompany = dataTable.getData("AssignmentEntry", "Company");
			String sBasicType = dataTable.getData("AssignmentEntry","SellerType");			
			if(reusablesSeller.login(sUsername, sPassword, sCurrThread)){
				Thread.sleep(30000);
				dl.writeHtmlTableRow(sCurrThread, "Login","to the application successful", "PASS");
				if (isDisplayedInUI(objEmu.selBy("//object[@uName='assignment']"))) {
					String sAssignmentEntryHeader = objEmu.selObj(driver,"//object[@uName='header']").getText();
					if (sAssignmentEntryHeader.equalsIgnoreCase("Assignment Entry")) {
						dl.writeHtmlTableRow(sCurrThread, "Assignment Entry","page navigation successful", "PASS");
						fnAssignmwntEntry_Basic(sCurrThread, sBasicType);
					} else {
						String sFile = msc.takeAndSaveScreenshot(driver,"/res/screenshot");
						dl.writeHtmlTableRow(sCurrThread, "Assignment Entry","page navigation unsuccessful" + sFile, "FAIL");
						System.setProperty("ro.pass", "FAIL");
					}
				} else {
					String sFile = msc.takeAndSaveScreenshot(driver,
							"/res/screenshot");
					dl.writeHtmlTableRow(sCurrThread, "Assignment",
							"navigation tab not visible<br> Kindly refer" + sFile,
							"FAIL");
					System.setProperty("ro.pass", "FAIL");
				}
			}else{
				String sFile = msc.takeAndSaveScreenshot(driver,"/res/screenshot");
				dl.writeHtmlTableRow(sCurrThread, "Login","to the application unsuccessful" + sFile, "FAIL");
				System.setProperty("ro.pass", "FAIL");
			}
		} catch (Exception e) {
			String sFile = msc.takeAndSaveScreenshot(driver, "/res/screenshot");
			dl.writeHtmlTableRow(sCurrThread, "Assignment Entry", "Failed<br>"
					+ e.getMessage() + "Kindly refer" + sFile, "FAIL");
			System.setProperty("ro.pass", "FAIL");
			e.printStackTrace();
		} finally {
			dl.writeHtmlTag(sCurrThread, "</table>");
			System.out.println("Status:" + System.getProperty("ro.pass"));
		}
	}

	public void fnAssignmwntEntry_Basic(String sCurrThread, String sBasicType) {
		try {
			String sCountry = dataTable.getData("AssignmentEntry", "Country");
			String sSeller = dataTable.getData("AssignmentEntry", "Seller");
			String sContactAdjuster = dataTable.getData("AssignmentEntry", "ContactAdjuster");
			String sLowsetLevelInHierarchy = dataTable.getData("AssignmentEntry", "LowsetLevelInHierarchy");
			String sItemGroup = dataTable.getData("AssignmentEntry", "ItemGroup");
			dl.writeHtmlTableRow(sCurrThread, "*****","Step 1: Basics", "DONE");
			sFieldDisplayStatus = fd.validateDisplayFields("AssignmentEntry", new String[] { "all" });
			dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
			if (sBasicType.equals("NonInsurance")) {
				dl.writeHtmlTableRow(sCurrThread, "Creating Lot","with Non-Insurance seller"+": "+sSeller, "DONE");
				dropDownSelect(objEmu.selBy("//object[@uName='select-operatingCountry']"),sCountry);
				dl.writeHtmlTableRow(sCurrThread, "Selected","Country"+": "+sCountry, "DONE");
				dropDownSelect(objEmu.selBy("//object[@uName='select-Seller']"),sSeller);
				dl.writeHtmlTableRow(sCurrThread, "Selected","Seller"+": "+sSeller, "DONE");
				dropDownSelect(objEmu.selBy("//object[@uName='select-LowsetLevelInHierarchy']"),sLowsetLevelInHierarchy);
				dl.writeHtmlTableRow(sCurrThread, "Selected","Lowset Level In Hierarchy"+": "+sLowsetLevelInHierarchy, "DONE");
				dropDownSelect(objEmu.selBy("//object[@uName='select-Contact']"),sContactAdjuster);
				dl.writeHtmlTableRow(sCurrThread, "Selected","Adjuster"+": "+sContactAdjuster, "DONE");
				objEmu.selObj(driver, "//object[@uName='Btn-Submit-Step1']").click();
				Thread.sleep(30000);
				fnAssignmwntEntry_ItemDescription(sCurrThread,sCountry,sBasicType,sItemGroup);
			} else if (sBasicType.equals("Insurance")) {
				dl.writeHtmlTableRow(sCurrThread, "Creating Lot","with Insurance seller"+": "+sSeller, "DONE");
				dropDownSelect(objEmu.selBy("//object[@uName='select-operatingCountry']"),sCountry);
				dl.writeHtmlTableRow(sCurrThread, "Selected","Country"+": "+sCountry, "DONE");
				dropDownSelect(objEmu.selBy("//object[@uName='select-Seller']"),sSeller);
				dl.writeHtmlTableRow(sCurrThread, "Selected","Seller"+": "+sSeller, "DONE");
				dropDownSelect(objEmu.selBy("//object[@uName='select-LowsetLevelInHierarchy']"),sLowsetLevelInHierarchy);
				dl.writeHtmlTableRow(sCurrThread, "Selected","Lowset Level In Hierarchy"+": "+sLowsetLevelInHierarchy, "DONE");
				dropDownSelect(objEmu.selBy("//object[@uName='select-Contact']"),sContactAdjuster);
				dl.writeHtmlTableRow(sCurrThread, "Selected","Contact"+": "+sContactAdjuster, "DONE");
				objEmu.selObj(driver, "//object[@uName='Btn-Submit-Step1']").click();
				Thread.sleep(30000);
				fnAssignmwntEntry_ItemDescription(sCurrThread,sCountry,sBasicType,sItemGroup);
			} else {
				String sFile = msc.takeAndSaveScreenshot(driver,"/res/screenshot");
				dl.writeHtmlTableRow(sCurrThread, "Basic Type","not available in application" + "<br> Kindly refer"+ sFile, "FAIL");
			}
		} catch (Exception e) {
			String sFile = msc.takeAndSaveScreenshot(driver, "/res/screenshot");
			dl.writeHtmlTableRow(sCurrThread, "Step 1: Basic",
					"Failed<br>" + e.getMessage() + "Kindly refer" + sFile,
					"FAIL");
			System.setProperty("ro.pass", "FAIL");
			e.printStackTrace();
		}
	}

	public void fnAssignmwntEntry_ItemDescription(String sCurrThread, String sCountry, String sBasicType, String sItemGroup) {
		try {
			String sItemType = dataTable.getData("AssignmentEntry", "ItemType");
			/*String sReferenceNumber = dataTable.getData("AssignmentEntry", "ReferenceNumber");
			String sRegistrationNumber = dataTable.getData("AssignmentEntry", "RegistrationNumber");
			String sFirstRegistrationDate = dataTable.getData("AssignmentEntry", "FirstRegistrationDate");
			String sVIN = dataTable.getData("AssignmentEntry", "VIN");
			*//** This will be generated runtime later. *//*
			String sYearOfManufacture = dataTable.getData("AssignmentEntry", "YearOfManufacture");
			String sMake = dataTable.getData("AssignmentEntry", "Make");
			String sMakeAvailable = dataTable.getData("AssignmentEntry", "MakeAvailable");
			String sModel = dataTable.getData("AssignmentEntry", "Model");
			String sModelAvailable = dataTable.getData("AssignmentEntry", "ModelAvailable");
			String sTrim = dataTable.getData("AssignmentEntry", "Trim");
			String sTrimAvailable = dataTable.getData("AssignmentEntry", "TrimAvailable");
			String sColor = dataTable.getData("AssignmentEntry", "Color");
			String sLicensePlateNumber = dataTable.getData("AssignmentEntry", "LicensePlateNumber");
			String sOdometer = dataTable.getData("AssignmentEntry", "Odometer");
			String sOdometerUnit = dataTable.getData("AssignmentEntry", "OdometerUnit");
			String sBodyStyle = dataTable.getData("AssignmentEntry", "BodyStyle");
			String sEngineOutput = dataTable.getData("AssignmentEntry",	"EngineOutput");
			String sEngineOutputUnit = dataTable.getData("AssignmentEntry",	"EngineOutputUnit");
			String sEngine = dataTable.getData("AssignmentEntry", "Engine");
			String sCylinders = dataTable.getData("AssignmentEntry", "Cylinders");
			String sCubicCapacity = dataTable.getData("AssignmentEntry", "CubicCapacity");
			String sTransmission = dataTable.getData("AssignmentEntry",	"Transmission");
			String sDrive = dataTable.getData("AssignmentEntry", "Drive");
			String sFuel = dataTable.getData("AssignmentEntry", "Fuel");
			String sCO2Emission = dataTable.getData("AssignmentEntry",	"CO2Emission");
			String sRepairCost = dataTable.getData("AssignmentEntry", "RepairCost");
			String sResidualValue = dataTable.getData("AssignmentEntry", "ResidualValue");
			String sItemValue = dataTable.getData("AssignmentEntry", "ItemValue");
			String sEuronorm = dataTable.getData("AssignmentEntry", "Euronorm");
			String sKeys = dataTable.getData("AssignmentEntry", "Keys");
			String sTowable = dataTable.getData("AssignmentEntry", "Towable");
			String sVATEligible = dataTable.getData("AssignmentEntry", "VATEligible");*/
			
			dropDownSelect(objEmu.selBy("//object[@uName='select-ItemType']"),sItemType);
			dl.writeHtmlTableRow(sCurrThread, "Selected","Item Type"+": "+sItemType, "DONE");
			Thread.sleep(20000);
			if(sBasicType.equals("NonInsurance")){				
				/*Check all commom fields*/
				sFieldDisplayStatus = fd.validateDisplayFieldsForMandatoryFields("AE-ItemDescription", new String[] {"all"});
				dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				/*Check fields as per Seller type*/
				sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] {"input-ReferenceNumber"});
				dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				/*Check fields as per Country*/
				if(!sCountry.equals("India")){
					sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] {"select-Euronorm"});
					dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				}
				if(sItemGroup.equals("Vehicle")||sItemGroup.equals("Industrial")||sItemGroup.equals("Marine")){
					sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] {"input-CO2Emission","input-EngineOutput","select-EngineOutputUnit","input-Odometer","select-OdometerUnit","input-LicensePlate","select-Color","input-YearOfManufacture","input-FirstRegistrationDate","input-RegistrationNumber"});
					dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				}
				if(sItemGroup.equals("Vehicle")||sItemGroup.equals("Industrial")){
					sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] {"input-VehicleIdentificationNumber"});
					dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				}
				if(sItemGroup.equals("Industrial")||sItemGroup.equals("Marine")){
					sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] {"input-GrossWeight","input-Length"});
					dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				}
				if(sItemGroup.equals("Marine")){
					sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] {"input-Marines-IdentificationNumber"});
					dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				}
				if(sItemGroup.equals("Vehicle")){
					sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] {"select-Fuel","select-Drive","select-Transmission","input-CubicCapacity","input-Cylinder","input-Engine","input-BodyStyle"});
					dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				}
				if(sItemGroup.equals("Homeowner")){
					sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] {"ae-itemdescriptionItemdescription"});
					dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				}
				if(isDisplayedInUI(objEmu.selBy("//object[@uName='input-Make']"))){
					sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] {"input-Make"});
					dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				}else{
					sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] {"select-Make"});
					dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				}
				if(isDisplayedInUI(objEmu.selBy("//object[@uName='input-Model']"))){
					sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] {"input-Model"});
					dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				}else{
					sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] {"select-Model"});
					dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				}if(isDisplayedInUI(objEmu.selBy("//object[@uName='input-Trim']"))){
					sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] {"input-Trim"});
					dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				}else{
					sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] {"select-Trim"});
					dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				}
			}else if(sBasicType.equals("Insurance")){
				/*Check all commom fields*/
				sFieldDisplayStatus = fd.validateDisplayFieldsForMandatoryFields("AE-ItemDescription", new String[] { "all" });
				dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				/*Check fields as per Country*/
				if(!sCountry.equals("India")){
					sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] { "select-Euronorm" });
					dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				}
				if(sItemGroup.equals("Vehicle")||sItemGroup.equals("Industrial")||sItemGroup.equals("Marine")){
					sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] {"input-CO2Emission","input-EngineOutput","select-EngineOutputUnit","input-Odometer","select-OdometerUnit","input-LicensePlate","select-Color","input-YearOfManufacture","input-FirstRegistrationDate","input-RegistrationNumber"});
					dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				}
				if(sItemGroup.equals("Vehicle")||sItemGroup.equals("Industrial")){
					sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] {"input-VehicleIdentificationNumber"});
					dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				}
				if(sItemGroup.equals("Industrial")||sItemGroup.equals("Marine")){
					sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] {"input-GrossWeight","input-Length"});
					dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				}
				if(sItemGroup.equals("Marine")){
					sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] {"input-Marines-IdentificationNumber"});
					dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				}
				if(sItemGroup.equals("Vehicle")){
					sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] {"select-Fuel","select-Drive","select-Transmission","input-CubicCapacity","input-Cylinder","input-Engine","input-BodyStyle"});
					dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				}
				if(isDisplayedInUI(objEmu.selBy("//object[@uName='input-Make']"))){
					sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] {"input-Make"});
					dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				}else{
					sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] {"select-Make"});
					dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				}
				if(isDisplayedInUI(objEmu.selBy("//object[@uName='input-Model']"))){
					sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] {"input-Model"});
					dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				}else{
					sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] {"select-Model"});
					dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				}if(isDisplayedInUI(objEmu.selBy("//object[@uName='input-Trim']"))){
					sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] {"input-Trim"});
					dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				}else{
					sFieldDisplayStatus= fd.validateDisplayFields("AE-ItemDescription", new String[] {"select-Trim"});
					dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
				}
			}else{
				
			}
			
			
			/*if (sItemGroup.equals("Vehicle")) {
				dropDownSelect(objEmu.selBy("//object[@uName='select-ItemType']"),sItemType);
				sFieldDisplayStatus = fd.validateDisplayFieldsForMandatoryFields("AE-ItemDescription", new String[] { "all" });
				dl.writeHtmlTableRow(sCurrThread, "Display Fields",sFieldDisplayStatus.split("~")[0],sFieldDisplayStatus.split("~")[1]);
			}else if(sItemGroup.equals("Vehicle")){

			}*/

		} catch (NoSuchElementException noSuchElementException) {
			String sFile = msc.takeAndSaveScreenshot(driver, "/res/screenshot");
			dl.writeHtmlTableRow(sCurrThread, "Step 2: Vehicle Description","Failed<br>" + " " + noSuchElementException.getMessage()+ "<br> Kindly refer" + sFile, "FAIL");
			System.setProperty("ro.pass", "FAIL");
			noSuchElementException.printStackTrace();
		} catch (Exception e) {
			String sFile = msc.takeAndSaveScreenshot(driver, "/res/screenshot");
			dl.writeHtmlTableRow(sCurrThread, "Step 2: Vehicle Description", "Failed<br>" + e.getMessage() + "Kindly refer" + sFile,"FAIL");
			System.setProperty("ro.pass", "FAIL");
			e.printStackTrace();
		}finally{
			dl.writeHtmlTag(sCurrThread, "</table>");
			System.out.println("Status:" + System.getProperty("ro.pass"));
		}

	}
}