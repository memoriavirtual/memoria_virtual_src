import {
  heightPercentageToDP as hp,
  widthPercentageToDP as wp,
} from 'react-native-responsive-screen';

const searchStyles = () => ({
  container: {
    flex: 1,
    justifyContent: 'flex-start',
    alignItems: 'flex-start',
    backgroundColor: '#fff',

  },
  list: {
    width: wp('90%'),
    height: hp('75%'),
  },
  item: {
    flex: 1,
    width: wp('90%'),
    paddingHorizontal: wp('2%'),
    paddingVertical: hp('3%'),
    fontSize: 20,

  },
  footerBar: {
    borderBottomColor: '#00000020',
    borderBottomWidth: 1,
    width: wp('100%'),
  },
  footnoteText: {
    fontSize: 14,
    marginTop: hp('5%'),
    textAlign: 'center',
  },
});

export default searchStyles;
