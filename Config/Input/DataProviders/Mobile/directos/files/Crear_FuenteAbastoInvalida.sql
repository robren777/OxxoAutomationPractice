

--Obtiene Sotre_ID
SELECT @STORE_ID_SOURCE = STORE_ID_SOURCE
FROM XposCloudSettingQA.dbo.STORE_PLACE 
WHERE @CR_STORE = CR_STORE AND @CR_PLACE = CR_PLACE;
 
--Obtiene cantidad maxima de pedidos a realizar por tienda
SELECT @CANTMAXPEDGEN=[VALUE] FROM XposCloudSettingQA.dbo.SYSTEM_PARAM 
	   WHERE (CODE = 'CANTMAXPED_GEN');

--Obtiene cantidad maxima de pedidos a recibir por proveedor-tienda
SELECT @CANTMAXPEDREC=[VALUE] FROM XposCloudSettingQA.dbo.SYSTEM_PARAM 
	   WHERE CODE = 'CANTMAXPED';

--Obtiene cantidad de pedidos abiertos y/o cerrados del proveedor
use XposCloudPurchaseToolQA
SELECT S.CD_NMB_SPR
	,COUNT(*) AS Ordenes
into #tmpPO
FROM dbo.TR_PURCHASEORDER H
LEFT JOIN PA_SPR S ON S.PA_SPR_ID = H.PA_SPR_ID
WHERE H.DATE_SUPPLY IS NULL AND (@STORE_ID_SOURCE = H.STORE_PLACE_ID)
GROUP BY S.CD_NMB_SPR ;
 
--Obtiene cantidad de pedidos cerrados del proveedor
SELECT S.CD_NMB_SPR
	,COUNT(*) AS Ordenes
into #tmpPOClosed
FROM dbo.TR_PURCHASEORDER H
LEFT JOIN PA_SPR S ON S.PA_SPR_ID = H.PA_SPR_ID
WHERE H.DATE_CLOSE IS NOT NULL 
AND  H.DATE_SUPPLY IS NULL AND (@STORE_ID_SOURCE = H.STORE_PLACE_ID)
GROUP BY S.CD_NMB_SPR ;
 
--Ordenes recibidas x provedor-hoy
Select S.CD_NMB_SPR
	,count(*) as totOCs
into #tmpPORec
from dbo.TR_PURCHASEORDER H
LEFT JOIN PA_SPR S ON S.PA_SPR_ID = H.PA_SPR_ID
where H.DATE_SUPPLY is not null AND (@STORE_ID_SOURCE = H.STORE_PLACE_ID)
	and convert(varchar(8),H.DATE_SUPPLY, 112)=convert(varchar(8),getdate(),112)
group by S.CD_NMB_SPR ;
 
CREATE TABLE #FAinvalida ( [SUPPLIER_NUM] [varchar](100) NOT NULL,
						 [SUPPLIER_NAME] [varchar](100) NOT NULL,
						 [STATUS] [bit] NOT NULL,
						 [Can_Create] [bit] NOT NULL, [Can_Receive] [bit] NOT NULL);
 
INSERT INTO #FAinvalida 
SELECT  CAST(C.CD_NMB_SPR AS VARCHAR(10)) AS SUPPLIER_NUM
			,C.NM_SPR AS SUPPLIER_NAME
			,CAST(case when @CANTMAXPEDGEN = 0
				then 1
				else
					case when @CANTMAXPEDGEN > isNull(PO.Ordenes,0)
						then 1
						else 0
					end
			end AS BIT)AS STATUS
			,convert(bit, case when isNull(POClosed.Ordenes, 0) = 0 then 1 else 0 end) as Can_Create
			,convert(bit, case when isNull(PoRec.totOCs,0)>= convert(int, @CANTMAXPEDREC) then 0 else 1 end) as Can_Receive
		FROM [XposCloudPurchaseToolQA].[dbo].[PA_SPR] C
			Left join #tmpPO PO
				on C.CD_NMB_SPR = PO.CD_NMB_SPR
			Left join #tmpPOClosed POClosed
				on C.CD_NMB_SPR = POClosed.CD_NMB_SPR
			left join #tmpPORec POREC
				on C.CD_NMB_SPR = POREC.CD_NMB_SPR
			left join [XposCloudPurchaseToolQA].[dbo].STORE_PLACE SP
				on C.STORE_PLACE_ID = SP.STORE_PLACE_ID
			WHERE SP.CR_PLACE = @CR_PLACE
			AND SP.CR_STORE = @CR_STORE
		ORDER BY c.cd_nmb_spr;
 
