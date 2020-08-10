import {
  heightPercentageToDP as hp,
  widthPercentageToDP as wp,
} from 'react-native-responsive-screen';

const itemCardStyles = () => ({
  // Common styles
  mainContainer: {
    // flex: 1,
    width: wp('90%'),
    justifyContent: 'flex-start',
    alignItems: 'center',
    paddingHorizontal: wp('5%'),
    paddingBottom: hp('2%'),
    marginVertical: hp('2%'),
    backgroundColor: '#FFFFFF',
    borderRadius: 20,
    borderWidth: 0.1,
    elevation: 10,
  },
  titleContainer: {
    justifyContent: 'center',
    alignItems: 'center',
    marginVertical: hp('2%'),

  },
  titleText: {
    textAlign: 'center',
    fontSize: 18,
    fontWeight: 'bold',
  },

  // Large text stypes
  valueLargeTextContainer: {
    width: wp('80%'),
    marginBottom: hp('2%'),
    paddingHorizontal: wp('3%'),
    paddingVertical: hp('3%'),
    borderRadius: 20,
    borderWidth: 0.2,
    borderColor: '#000000',
    backgroundColor: '#FFFFFF'
  },
  valueLargeText: {
    textAlign: 'justify',
  },

  // Text (single line) styles
  valueTextContainer: {
    width: wp('80%'),
    marginBottom: hp('2%'),
    paddingHorizontal: wp('3%'),
    paddingVertical: hp('2%'),
    borderRadius: 20,
    borderWidth: 0.2,
    borderColor: '#000000',
    backgroundColor: '#FFFFFF'
  },
  valueText: {
    textAlign: 'left',
  },

  // Switch styles
  valueSwitchContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    width: wp('30%'),
    marginBottom: hp('2%'),
    paddingHorizontal: wp('3%'),
    paddingVertical: hp('2%'),
    borderRadius: 20,
    borderWidth: 0.2,
    borderColor: '#000000',
    backgroundColor: '#FFFFFF'
  },

  // Researchers styles
  itemResearcherContainer: {
    width: wp('80%'),
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'flex-start',
  },
  itemResearcherLineContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    marginVertical: hp('2%'),
  },
  itemResearcherTopicText: {
    fontSize: 16,
    fontWeight: 'bold',
  },

  // Related goods styles
  relatedGoodsContainer: {
    width: wp('80%'),
    marginBottom: hp('2%'),
    paddingHorizontal: wp('3%'),
    paddingVertical: hp('2%'),
    borderRadius: 20,
    borderWidth: 0.5,
    borderColor: '#000000',
    backgroundColor: '#F2F2F2'
  },

  relatedGoodTouchableText: {
    fontSize: 18,
    fontWeight: 'bold',
    textAlign: 'center',
  },
});

export default itemCardStyles;