/*
 * This class is still be considered, due to the unfulfilled categories/tags requirement
 * and will be implemented in some next days
 */
package com.inspireon.chuyentrolinhtinh.model.domain.post;

import java.util.ArrayList;
import java.util.List;

import com.inspireon.chuyentrolinhtinh.model.domain.shared.ValueObject;

public enum Tag implements ValueObject<Tag> {
	
	// revise tag name later
	ALL ("Tất cả", "", true),
	
	LIFE_STORY ("Chuyện cuộc sống", 
				"Nơi chia sẻ câu chuyện về những gì chúng ta đã trải qua trong cuộc đời, hoặc những câu chuyện thực đã truyền cảm hứng cho bạn.", true), 
	
	LOVE_STORY ("Chuyện tình yêu", 
				"Nơi chia sẻ chuyện tình yêu của bạn, những giây phút ngọt ngào hay những lỗi lầm khi yêu, "
			       + "và cả những mối tình thầm lặng mà bạn chưa bao giờ muốn nói ra. Hãy để mọi người cùng lắng nghe :)", true), 
	
    JOURNEY_STORY ("Chuyện nơi xa", 
    				"Nếu bạn đã từng đi xa, đến những miền đất lạ lẫm, "
    		       + "hoặc đã có những trải nghiệm kỳ thú với những nền văn hóa khác nhau, "
    		       + "xin hãy chia sẻ với chúng tôi – những người chưa có may mắn được tới những miền đất đó.", true), 
    		       
	PROFESSION_STORY ("Chuyện nghề", 
					"Bạn là ai? Bạn làm gì? Hãy chia sẻ cho chúng tôi một cuộc sống khác và những trải nghiệm khó quên trong sự nghiệp của bạn.", true),
	
	NEWS ("Thời sự", 
			"Nơi bạn chia sẻ những câu chuyện, trải nghiệm về những vấn đề thời sự hoặc những vấn đề tuy đã cũ nhưng vẫn còn vẹn nguyên tính thời sự.", true),
			
	GAMING ("Gaming", 
			"Nơi chia sẻ về game bạn yêu thích, hoăc cùng các thành viên khác tham gia vào các trò chơi ngay tại MyStory", true),
	
	ASK_FOR_STORY ("Hỏi chuyện", 
					"Bạn có một câu hỏi trong đầu muốn tìm lời giải? Bạn có chút thắc mắc không biết nên hỏi ai? "
				   + "Hay đơn giản là bạn muốn nghe một câu chuyện hay, một lời tâm sự cho chủ đề mình thích? Đây là nơi dành cho bạn.", true),
				   
	REVIEW ("Cảm nhận", 
			"Nơi chia sẻ quan điểm hay cảm nhận của bạn về một bộ phim, một bài thơ, hay một đội bóng mà bạn ưa thích.", true),
	
	IDEA ("Ý kiến", 
			"Nơi mà BQT dành nhiều thời gian đọc nhất trong ngày – hãy cho chúng tôi biết cảm nhận của bạn về website nhé!", true);

	private Tag(final String displayName, final String description, final boolean isDisplay) {
		this.displayName = displayName;
		this.description = description;
		this.isDisplay = isDisplay;
	}
	
	private final String displayName;
	private final String description;
	private final boolean isDisplay;
	
	public String displayName() {
		return displayName;
	}
	
	public String description() {
		return description;
	}
	
	public boolean isDisplay() {
		return isDisplay;
	}

	@Override
	public boolean sameValueAs(final Tag other) {
		return this.equals(other);
	}
	
	public static List<Tag> defaultDisplayTags() {
		List<Tag> defaultDisplayTags = new ArrayList<>();
		
		for (Tag tag : Tag.values()) {
			if (tag.isDisplay() == true) {
				defaultDisplayTags.add(tag);
			}
		}
		
		return defaultDisplayTags;
	}
}
