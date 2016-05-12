package es.fdi.reservas.storage.business.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class StorageObjectId implements Serializable {

	/**
	 * @see java.io.Serializable
	 */
	private static final long serialVersionUID = 3197591465440824793L;

	@Column(name="bucketName", length=50)
	private String bucket;
	
	@Column(name="objectKey", length=150)
	private String key;

	StorageObjectId() {
		
	}
	
	public StorageObjectId(String bucket, String key) {
		this.bucket = bucket;
		this.key = key;
	}
	
	public String getBucket() {
		return bucket;
	}

	public String getKey() {
		return key;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bucket == null) ? 0 : bucket.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StorageObjectId other = (StorageObjectId) obj;
		if (bucket == null) {
			if (other.bucket != null)
				return false;
		} else if (!bucket.equals(other.bucket))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "StorageObjectId [bucket=>'"+bucket+"', key=>'"+key+"']";
	}
}
