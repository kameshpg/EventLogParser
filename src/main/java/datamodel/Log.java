package datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "logs")
public class Log {
	
	public long getId_event() {
		return id_event;
	}

	public void setId_event(long id_event) {
		this.id_event = id_event;
	}

	@Override
	public String toString() {
		return "Log [id_event=" + id_event + ", id=" + id + ", state=" + state + ", timestamp=" + timestamp
				+ ", timestampStart=" + timestampStart + ", timestampEnd=" + timestampEnd + ", type=" + type + ", host="
				+ host + ", duration=" + duration + ", alert=" + alert + "]";
	}

	public long getTimestampStart() {
		return timestampStart;
	}

	public void setTimestampStart(long timestampStart) {
		this.timestampStart = timestampStart;
	}

	public long getTimestampEnd() {
		return timestampEnd;
	}

	public void setTimestampEnd(long timestampEnd) {
		this.timestampEnd = timestampEnd;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		
		this.state = state;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (alert ? 1231 : 1237);
		result = prime * result + (int) (duration ^ (duration >>> 32));
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (int) (id_event ^ (id_event >>> 32));
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
		result = prime * result + (int) (timestampEnd ^ (timestampEnd >>> 32));
		result = prime * result + (int) (timestampStart ^ (timestampStart >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Log))
			return false;
		Log other = (Log) obj;
		if (alert != other.alert)
			return false;
		if (duration != other.duration)
			return false;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (id_event != other.id_event)
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (timestamp != other.timestamp)
			return false;
		if (timestampEnd != other.timestampEnd)
			return false;
		if (timestampStart != other.timestampStart)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getId() {
		return id;
	}

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private long id_event;
	
	@Column(name = "event_id")
	private String id;
	
	@Column(name = "state")
	private String state;
	
	@Transient
	private long timestamp;
	
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Column(name = "starttime")
	private long timestampStart;
	
	@Column(name = "endtime")
	private long timestampEnd;
	
	
	@Column(name = "logType")
	private String type;
	
	@Column(name = "host")
	private String host;
	
	@Column(name = "duration")
	private long duration;
	
	@Column(name = "alert")
	private boolean alert;

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public boolean isAlert() {
		return alert;
	}

	public void setAlert(boolean alert) {
		this.alert = alert;
	}
	
	

}
