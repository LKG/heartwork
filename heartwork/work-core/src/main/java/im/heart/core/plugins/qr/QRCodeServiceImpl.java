package im.heart.core.plugins.qr;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Hashtable;
import org.apache.commons.codec.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import im.heart.core.plugins.qr.utils.ImageUtils;
import im.heart.core.plugins.qr.utils.MatrixToImageWriter;
import im.heart.core.utils.OkHttpClientUtils;
import im.heart.core.utils.StringUtilsEx;

/**
 * 
 * @author lkg
 * @desc 二维码生成接口
 */
@Service(value = QRCodeService.BEAN_NAME)
@Transactional(propagation = Propagation.SUPPORTS)
public class QRCodeServiceImpl implements QRCodeService {
	protected static final Logger logger = LoggerFactory.getLogger(QRCodeServiceImpl.class);


	@Override
	public BufferedImage generateQRcodeImage(String contents) {
		return this.generateQRcodeImage(contents, 200, 200,30,
				ErrorCorrectionLevel.L,null);
	}


	@Override
	public BufferedImage generateQRcodeImage(String contents, int width,
			int height, int margin, ErrorCorrectionLevel errorCorrectionLevel,String logoUrl) {
		if (errorCorrectionLevel == null){
			errorCorrectionLevel = ErrorCorrectionLevel.L;
		}
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		// 指定纠错等级
		hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel);
		// 指定编码格式
		hints.put(EncodeHintType.CHARACTER_SET, Charsets.UTF_8.toString());
		hints.put(EncodeHintType.MARGIN, margin);
		BitMatrix bitMatrix;
		try {
			if(StringUtilsEx.isNotBlank(logoUrl)){
				 byte[] imgBytes = OkHttpClientUtils.fetchEntity(logoUrl,null);
				BufferedImage image=ImageUtils.tBoufferedImageByByte(imgBytes);
				bitMatrix = new MultiFormatWriter().encode(contents,
						BarcodeFormat.QR_CODE, width, height, hints);
				return MatrixToImageWriter.tolOGOBufferedImage(bitMatrix,image);
			}
			
			bitMatrix = new MultiFormatWriter().encode(contents,
					BarcodeFormat.QR_CODE, width, height, hints);

			return MatrixToImageWriter.toBufferedImage(bitMatrix);
		} catch (IOException e) {
			logger.error(e.getStackTrace()[0].getMethodName(), e);
		} catch (WriterException e) {
			logger.error(e.getStackTrace()[0].getMethodName(), e);
		}

		return null;

	}

}
