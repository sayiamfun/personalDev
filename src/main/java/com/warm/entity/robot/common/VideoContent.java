package com.warm.entity.robot.common;





public class VideoContent
{
	private String thumbUrl;
	private Integer videoLength;
	private String videoUrl;

	public VideoContent(final String videoUrl, final String thumbUrl, final Integer videoLength)
	{
		super();
		this.videoUrl = videoUrl;
		this.thumbUrl = thumbUrl;
		this.videoLength = videoLength;
	}

	private String thumbName()
	{
		final int index = this.thumbUrl.indexOf("imgMsg/");
		if (index == -1)
		{
			return null;
		}
		final String substring = this.thumbUrl.substring(index + "imgMsg/".length());
		return substring.substring(0, substring.indexOf("."));
	}

	private boolean validName()
	{
		final String videoName = this.videoName();
		final String thumbName = this.thumbName();
		return videoName != null && thumbName != null && videoName.equals(thumbName);
	}

	private String videoName()
	{
		final int index = this.videoUrl.indexOf("videoMsg/");
		if (index == -1)
		{
			return null;
		}
		final String substring = this.videoUrl.substring(index + "videoMsg/".length());
		return substring.substring(0, substring.indexOf("."));
	}

	protected boolean canEqual(final Object o)
	{
		return o instanceof VideoContent;
	}

	@Override
	public boolean equals(final Object o)
	{
		if (o != this)
		{
			if (!(o instanceof VideoContent))
			{
				return false;
			}
			final VideoContent videoContent = (VideoContent) o;
			if (!videoContent.canEqual(this))
			{
				return false;
			}
			final String videoUrl = this.getVideoUrl();
			final String videoUrl2 = videoContent.getVideoUrl();
			Label_0059:
			{
				if (videoUrl == null)
				{
					if (videoUrl2 == null)
					{
						break Label_0059;
					}
				}
				else if (videoUrl.equals(videoUrl2))
				{
					break Label_0059;
				}
				return false;
			}
			final String thumbUrl = this.getThumbUrl();
			final String thumbUrl2 = videoContent.getThumbUrl();
			Label_0087:
			{
				if (thumbUrl == null)
				{
					if (thumbUrl2 == null)
					{
						break Label_0087;
					}
				}
				else if (thumbUrl.equals(thumbUrl2))
				{
					break Label_0087;
				}
				return false;
			}
			final Integer videoLength = this.getVideoLength();
			final Integer videoLength2 = videoContent.getVideoLength();
			if (videoLength == null)
			{
				if (videoLength2 == null)
				{
					return true;
				}
			}
			else if (videoLength.equals(videoLength2))
			{
				return true;
			}
			return false;
		}
		return true;
	}

	public String getThumbUrl()
	{
		return this.thumbUrl;
	}

	public Integer getVideoLength()
	{
		return this.videoLength;
	}

	public String getVideoUrl()
	{
		return this.videoUrl;
	}

	@Override
	public int hashCode()
	{
		int hashCode = 43;
		final String videoUrl = this.getVideoUrl();
		int hashCode2;
		if (videoUrl == null)
		{
			hashCode2 = 43;
		}
		else
		{
			hashCode2 = videoUrl.hashCode();
		}
		final String thumbUrl = this.getThumbUrl();
		int hashCode3;
		if (thumbUrl == null)
		{
			hashCode3 = 43;
		}
		else
		{
			hashCode3 = thumbUrl.hashCode();
		}
		final Integer videoLength = this.getVideoLength();
		if (videoLength != null)
		{
			hashCode = videoLength.hashCode();
		}
		return (hashCode3 + (hashCode2 + 59) * 59) * 59 + hashCode;
	}

	public void setThumbUrl(final String thumbUrl)
	{
		this.thumbUrl = thumbUrl;
	}

	public void setVideoLength(final Integer videoLength)
	{
		this.videoLength = videoLength;
	}

	public void setVideoUrl(final String videoUrl)
	{
		this.videoUrl = videoUrl;
	}

	@Override
	public String toString()
	{
		return "VideoContent(videoUrl=" + this.getVideoUrl() + ", thumbUrl=" + this.getThumbUrl() + ", videoLength=" + this.getVideoLength() + ")";
	}
}
