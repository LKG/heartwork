package im.heart.core.plugins.captcha;

import im.heart.core.utils.StringUtilsEx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;

import org.springframework.stereotype.Component;
@Component
public class ImageCaptcha  implements Captcha {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3925837584824450990L;

	private Boolean hasChallengeBeenCalled = Boolean.FALSE;

    protected String question;
    
    public ImageCaptcha() {
		super();
	}
	private Boolean caseSensitive=Boolean.FALSE;//是否大小写敏感
	
    protected transient BufferedImage challenge;

    protected ImageCaptcha(String question, BufferedImage challenge) {
        this.challenge = challenge;
        this.question = question;
    }

    /**
     * Accessor captcha question
     *
     * @return the question
     */
    @Override
    public final String getQuestion() {
        return question;
    }

    /**
     * @return the challenge
     */
    @Override
    public final BufferedImage getChallenge() {
        return getImageChallenge();
    }

    /**
     * @return the image challenge
     */
    public final BufferedImage getImageChallenge() {
        hasChallengeBeenCalled = Boolean.TRUE;
        return challenge;
    }


    /**
     * Dispose the challenge, once this method is call the getChallenge method will return null.<br> It has been added
     * for technical reasons : a captcha is always used in a two step fashion<br> First submit the challenge, and then
     * wait until the response arrives.<br> It had been asked to have a method to dispose the challenge that is no
     * longer used after being dipslayed. So here it is!
     */
    public final void disposeChallenge() {
        this.challenge = null;
    }

    /**
     * This method should return true if the getChalenge method has been called (has been added in order to properly
     * manage the captcha state.
     *
     * @return true if getChallenge has been called false otherwise.
     */
    @Override
    public Boolean hasGetChalengeBeenCalled() {
        return hasChallengeBeenCalled;
    }

    /**
     * This method have to be implemented in order to serialize the image challenge to JPEG format
     * @param out The target outputStream in which the captcha will be serialized
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream out)
            throws IOException {
        // Serialize captcha fields with defaut method
        out.defaultWriteObject();
        // If the challenge has not been disposed
        if (this.challenge != null) {
            // use png encoding
            ImageIO.write(this.challenge, "png", new MemoryCacheImageOutputStream(out));
        }
    }

    /**
     * This method have to be implemented in order to unserialize the image challenge from JPEG format
     * @param in The source inputStream from which the captcha will be unserialized
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        // UnSerialize captcha fields with default method
        in.defaultReadObject();
        try {
            this.challenge =ImageIO.read(new MemoryCacheImageInputStream(in));
        } catch (IOException e) {
            if (!hasChallengeBeenCalled.booleanValue()) {
                throw e;
            }
        }
    }

	@Override
	public Boolean validateResponse(final String response) {
		if(StringUtilsEx.isNotBlank(response)){
			 return caseSensitive? response.equals(this.question) : response.equalsIgnoreCase(this.question);
		}
		return  Boolean.FALSE;
	}

	public Boolean getHasChallengeBeenCalled() {
		return hasChallengeBeenCalled;
	}

	public void setHasChallengeBeenCalled(Boolean hasChallengeBeenCalled) {
		this.hasChallengeBeenCalled = hasChallengeBeenCalled;
	}

	public Boolean getCaseSensitive() {
		return caseSensitive;
	}

	public void setCaseSensitive(Boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setChallenge(BufferedImage challenge) {
		this.challenge = challenge;
	}
	
}
